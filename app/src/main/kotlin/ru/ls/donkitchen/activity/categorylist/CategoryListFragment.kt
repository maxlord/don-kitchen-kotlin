package ru.ls.donkitchen.activity.categorylist

import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.j256.ormlite.dao.Dao
import kotlinx.android.synthetic.main.fragment_category_list.*
import ru.ls.donkitchen.R
import ru.ls.donkitchen.activity.base.SchedulersManager
import ru.ls.donkitchen.activity.receiptlist.ReceiptList
import ru.ls.donkitchen.app.DonKitchenApplication
import ru.ls.donkitchen.db.DatabaseHelper
import ru.ls.donkitchen.db.table.Category
import ru.ls.donkitchen.fragment.base.BaseFragment
import ru.ls.donkitchen.navigateActivity
import ru.ls.donkitchen.rest.Api
import ru.ls.donkitchen.rest.model.response.CategoryListResult
import rx.Observable
import rx.lang.kotlin.subscribeWith
import timber.log.Timber
import java.sql.SQLException
import java.util.*
import javax.inject.Inject

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 16.10.16
 */
class CategoryListFragment : BaseFragment() {
    @Inject lateinit var application: DonKitchenApplication
    @Inject lateinit var api: Api
    @Inject lateinit var schedulersManager: SchedulersManager
    @Inject lateinit var databaseHelper: DatabaseHelper

    private var adapter: CategoryAdapter? = null

    override fun getLayoutRes(): Int {
        return R.layout.fragment_category_list
    }

    override fun inject() {
        getComponent().inject(this)
    }

    override fun initControls(v: View?) {
//        setHasOptionsMenu(true)

        list.setHasFixedSize(true)

        adapter = CategoryAdapter(activity, object : CategoryAdapter.Callback {
            override fun onItemClick(item: CategoryListResult.CategoryItem) {
                val b = Bundle()
                b.putInt(ReceiptList.EXT_IN_CATEGORY_ID, item.id)
                b.putString(ReceiptList.EXT_IN_CATEGORY_NAME, item.name)

                navigateActivity<ReceiptList>(false, b)
            }
        })
        list.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        list.adapter = adapter
    }

    override fun loadData() {


    }

    override fun onResume() {
        super.onResume()

        reloadData()
    }

    private fun refreshData(result: List<CategoryListResult.CategoryItem>?) {
        try {
            if (activity != null && result != null) {
                progress.visibility = View.GONE

                adapter?.clear()
                adapter?.addAllItems(result)
                adapter?.notifyDataSetChanged()

                if (result.isEmpty()) {
                    list.visibility = View.GONE
                    empty.visibility = View.VISIBLE
                } else {
                    list.visibility = View.VISIBLE
                    empty.visibility = View.GONE
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Ошибка получения списка категорий")
        }

    }

    private fun reloadData() {
        // Обновляем список категорий
        // сеть
        val network = api.getCategories().map(CategoryListResult::categories)
        // сохранение/обновление в БД из сети
        val networkWithSave = network.doOnNext { data ->
            Timber.i("Сохраняем категории в БД")

            if (data != null && !data.isEmpty()) {
                try {
                    val dao = databaseHelper.getDao<Dao<Category, Int>, Category>(Category::class.java)

                    for (ci in data) {
                        val item = Category()
                        item.id = ci.id
                        item.name = ci.name
                        item.imageLink = ci.imageLink
                        item.priority = ci.priority
                        item.receiptCount = ci.receiptCount

                        dao.createOrUpdate(item)
                    }
                } catch (e: SQLException) {
                    Timber.e(e, "Ошибка сохранения категорий в БД")
                }
            }
        }
        // БД
        val db = Observable.create<List<CategoryListResult.CategoryItem>> { s ->
            Timber.i("Получаем категории из БД")

            try {
                val dao = databaseHelper.getDao<Dao<Category, Int>, Category>(Category::class.java)
                val qb = dao.queryBuilder()
                qb.orderBy(Category.PRIORITY, true)
                val categories = qb.query()
                val categoryItems = ArrayList<CategoryListResult.CategoryItem>()
                if (!categories.isEmpty()) {
                    // Выполняем перепаковку объектов
                    for (c in categories) {
                        val item = CategoryListResult.CategoryItem()
                        item.id = c.id
                        item.name = c.name
                        item.imageLink = c.imageLink
                        item.priority = c.priority
                        item.receiptCount = c.receiptCount

                        categoryItems.add(item)
                    }
                }

                s.onNext(categoryItems)

                s.onCompleted()
            } catch (e: SQLException) {
                s.onError(e)
            }
        }

        // Выполняем все запросы
        Observable.concat(db, networkWithSave)
                .compose(schedulersManager.applySchedulers<List<CategoryListResult.CategoryItem>?>())
                .subscribeWith {
                    onNext {
                        Timber.i("Обновляем UI")

                        refreshData(it)
                    }

                    onError {
                        Timber.e(it, "Ошибка при загрузке категорий")

                        if (activity != null) {
                            progress.visibility = View.GONE

                            if (adapter?.itemCount == 0) {
                                list.visibility = View.GONE
                                empty.text = "Во время загрузки данных произошла ошибка!"
                                empty.visibility = View.VISIBLE
                            } else {
                                list.visibility = View.VISIBLE
                                empty.visibility = View.GONE
                            }
                        }
                    }
                }
    }

    companion object {

        fun newInstance(): CategoryListFragment {
            val args = Bundle()

            val fragment = CategoryListFragment()
            fragment.arguments = args

            return fragment
        }
    }
}
