package ru.ls.donkitchen.activity.receiptlist

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.j256.ormlite.dao.Dao
import kotlinx.android.synthetic.main.fragment_receipt_list.*
import kotlinx.android.synthetic.main.widget_toolbar.*
import org.jetbrains.anko.appcompat.v7.navigationIconResource
import ru.ls.donkitchen.R
import ru.ls.donkitchen.activity.base.SchedulersManager
import ru.ls.donkitchen.activity.receiptdetail.ReceiptDetail
import ru.ls.donkitchen.app.DonKitchenApplication
import ru.ls.donkitchen.db.DatabaseHelper
import ru.ls.donkitchen.db.table.Category
import ru.ls.donkitchen.db.table.Receipt
import ru.ls.donkitchen.fragment.base.BaseFragment
import ru.ls.donkitchen.navigateActivity
import ru.ls.donkitchen.rest.Api
import ru.ls.donkitchen.rest.model.response.ReceiptListResult
import rx.Observable
import rx.Observer
import rx.lang.kotlin.subscribeWith
import timber.log.Timber
import java.sql.SQLException
import java.util.*
import javax.inject.Inject
import kotlin.comparisons.compareBy

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 17.10.16
 */
class ReceiptListFragment : BaseFragment(), Observer<List<ReceiptListResult.ReceiptItem>> {
    @Inject lateinit var application: DonKitchenApplication
    @Inject lateinit var api: Api
    @Inject lateinit var schedulersManager: SchedulersManager
    @Inject lateinit var databaseHelper: DatabaseHelper

    private var adapter: ReceiptAdapter? = null
    private var categoryId: Int = 0
    private var categoryName: String? = null

    companion object {
        fun newInstance(categoryId: Int, categoryName: String): ReceiptListFragment {
            val args = Bundle()
            args.putInt(ReceiptList.EXT_IN_CATEGORY_ID, categoryId)
            args.putString(ReceiptList.EXT_IN_CATEGORY_NAME, categoryName)

            val fragment = ReceiptListFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_receipt_list
    }

    override fun inject() {
        getComponent().inject(this)
    }

    override fun initControls(v: View?) {
        setHasOptionsMenu(true)

//        toolbar_icon.visibility = View.VISIBLE
//        toolbar_icon.onClick {
//            activity.finish()
//        }

        val recycledViewPool = RecyclerView.RecycledViewPool()
        recycledViewPool.setMaxRecycledViews(1, 10)
        list.recycledViewPool = recycledViewPool
        list.setHasFixedSize(true)

        adapter = ReceiptAdapter(activity, object: ReceiptAdapter.Callback {
            override fun onItemClick(item: ReceiptListResult.ReceiptItem) {
                val b = Bundle()
                b.putInt(ReceiptDetail.EXT_IN_RECEIPT_ID, item.id)
                b.putString(ReceiptDetail.EXT_IN_RECEIPT_NAME, item.name)

                navigateActivity<ReceiptDetail>(false, b)
            }
        })
        list.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        list.adapter = adapter
    }

    override fun loadData() {
        if (arguments != null) {
            categoryId = arguments.getInt(ReceiptList.EXT_IN_CATEGORY_ID, 0)
            categoryName = arguments.getString(ReceiptList.EXT_IN_CATEGORY_NAME)
        }

        toolbar?.title = categoryName
        toolbar?.navigationIconResource = R.drawable.ic_arrow_back
        toolbar?.setNavigationOnClickListener { activity?.onBackPressed() }
    }

    override fun onResume() {
        super.onResume()

        reloadData()
    }

    override fun onCompleted() {

    }

    override fun onError(e: Throwable) {
        Timber.e(e, "Ошибка при загрузке рецептов")

        if (activity != null) {
            progress!!.visibility = View.GONE

            if (adapter!!.itemCount == 0) {
                list.visibility = View.GONE
                empty!!.visibility = View.VISIBLE
            } else {
                list.visibility = View.VISIBLE
                empty!!.visibility = View.GONE
            }
        }
    }

    override fun onNext(result: List<ReceiptListResult.ReceiptItem>) {
        Timber.i("Обновляем UI")

        refreshData(result)
    }

    private fun refreshData(result: List<ReceiptListResult.ReceiptItem>?) {
        try {
            if (activity != null && result != null) {
                progress!!.visibility = View.GONE

                if (result.isEmpty()) {
                    list.visibility = View.GONE
                    empty.visibility = View.VISIBLE
                } else {
                    // Пересортировываем список рецептов: рейтинг по-убыванию, название по-возрастанию
                    val sortedResult = result.sortedWith(compareBy({ it.rating }, { it.views })).reversed()

                    adapter?.clear()
                    adapter?.addAllItems(sortedResult)
                    adapter?.notifyDataSetChanged()

                    list.visibility = View.VISIBLE
                    empty.visibility = View.GONE
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Ошибка получения списка рецептов")
        }

    }

    private fun reloadData() {
        // Обновляем список рецептов
        // сеть
        val network = api.getReceipts(categoryId).map { it.receipts }
        // сохранение/обновление в БД из сети
        val networkWithSave = network.doOnNext { data ->
            Timber.i("Сохраняем рецепты в БД")

            if (data != null && !data.isEmpty()) {
                try {
                    val categoryDao = databaseHelper.getDao<Dao<Category, Int>, Category>(Category::class.java)
                    val receiptDao = databaseHelper.getDao<Dao<Receipt, Int>, Receipt>(Receipt::class.java)

                    for (ri in data) {
                        // Получаем категорию
                        val receiptCategory = categoryDao.queryForId(ri.categoryId)

                        val item = Receipt()
                        item.id = ri.id
                        item.name = ri.name
                        item.ingredients = ri.ingredients
                        item.receipt = ri.receipt
                        item.imageLink = ri.imageLink
                        item.category = receiptCategory
                        item.viewsCount = ri.views
                        item.rating = ri.rating

                        receiptDao.createOrUpdate(item)
                    }
                } catch (e: SQLException) {
                    Timber.e(e, "Ошибка сохранения рецептов в БД")
                }

            }
        }
        // БД
        val db = Observable.create<List<ReceiptListResult.ReceiptItem>> { s ->
            Timber.i("Получаем рецепты из БД")

            try {
                val dao = databaseHelper.getDao<Dao<Receipt, Int>, Receipt>(Receipt::class.java)
                val qb = dao.queryBuilder()
                qb.where().eq(Receipt.CATEGORY_ID, categoryId)
                qb.orderBy(Receipt.RATING, false)
                qb.orderBy(Receipt.VIEWS_COUNT, false)
                qb.orderBy(Receipt.NAME, true)
                val receipts = qb.query()
                val receiptItems = ArrayList<ReceiptListResult.ReceiptItem>()
                if (!receipts.isEmpty()) {
                    // Выполняем перепаковку объектов
                    for (r in receipts) {
                        val item = ReceiptListResult.ReceiptItem()

                        item.id = r.id
                        item.name = r.name
                        item.ingredients = r.ingredients
                        item.receipt = r.receipt
                        item.views = r.viewsCount
                        item.categoryId = r.category!!.id
                        item.categoryName = r.category!!.name
                        item.imageLink = r.imageLink
                        item.rating = r.rating

                        receiptItems.add(item)
                    }
                }

                s.onNext(receiptItems)

                s.onCompleted()
            } catch (e: SQLException) {
                s.onError(e)
            }
        }

        // Выполняем все запросы
        Observable.concat(db, networkWithSave)
                .compose(schedulersManager.applySchedulers<List<ReceiptListResult.ReceiptItem>?>())
                .subscribeWith {
                    onNext {
                        Timber.i("Обновляем UI")

                        refreshData(it)
                    }

                    onError {
                        Timber.e(it, "Ошибка при загрузке рецептов")

                        if (activity != null) {
                            progress!!.visibility = View.GONE

                            if (adapter!!.itemCount == 0) {
                                list.visibility = View.GONE
                                empty!!.visibility = View.VISIBLE
                            } else {
                                list.visibility = View.VISIBLE
                                empty!!.visibility = View.GONE
                            }
                        }
                    }
                }
    }
}
