package ru.ls.donkitchen.activity.categorylist

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_category_list.*
import ru.ls.donkitchen.R
import ru.ls.donkitchen.activity.receiptlist.ReceiptList
import ru.ls.donkitchen.fragment.base.BaseFragment
import ru.ls.donkitchen.navigateActivity

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 16.10.16
 */

class CategoryListFragment : BaseFragment(), CategoryListView {
    @InjectPresenter lateinit var presenter: CategoryListPresenter

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun displayError(error: String) {
        Snackbar.make(view!!, error, Snackbar.LENGTH_SHORT).show()
    }

    override fun displayNoData() {
        list.visibility = View.GONE
        empty.visibility = View.VISIBLE
    }

    override fun displayCategories(categories: List<CategoryViewItem>) {
        list.visibility = View.VISIBLE
        empty.visibility = View.GONE

        adapter?.clear()
        adapter?.addAllItems(categories)
        adapter?.notifyDataSetChanged()
    }

    private var adapter: CategoryAdapter? = null

    override fun getLayoutRes(): Int {
        return R.layout.fragment_category_list
    }

    override fun inject() {
        getComponent().plus(CategoryListModule()).inject(presenter)
    }

    override fun initControls(v: View?) {
        list.setHasFixedSize(true)

        adapter = CategoryAdapter(activity, object : CategoryAdapter.Callback {
            override fun onItemClick(item: CategoryViewItem) {
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
        presenter.loadData()
    }



    private fun reloadData() {
//        // Обновляем список категорий
//        // сеть
//        val network = api.getCategories().map(CategoryListResult::categories)
//        // сохранение/обновление в БД из сети
//        val networkWithSave = network.doOnNext { data ->
//            Timber.i("Сохраняем категории в БД")
//
//            if (data != null && !data.isEmpty()) {
//                try {
//                    val dao = databaseHelper.getDao<Dao<Category, Int>, Category>(Category::class.java)
//
//                    for (ci in data) {
//                        val item = Category()
//                        item.id = ci.id
//                        item.name = ci.name
//                        item.imageLink = ci.imageLink
//                        item.priority = ci.priority
//                        item.receiptCount = ci.receiptCount
//
//                        dao.createOrUpdate(item)
//                    }
//                } catch (e: SQLException) {
//                    Timber.e(e, "Ошибка сохранения категорий в БД")
//                }
//            }
//        }
//        // БД
//        val db = Observable.create<List<CategoryListResult.CategoryItem>> { s ->
//            Timber.i("Получаем категории из БД")
//
//            try {
//                val dao = databaseHelper.getDao<Dao<Category, Int>, Category>(Category::class.java)
//                val qb = dao.queryBuilder()
//                qb.orderBy(Category.PRIORITY, true)
//                val categories = qb.query()
//                val categoryItems = ArrayList<CategoryListResult.CategoryItem>()
//                if (!categories.isEmpty()) {
//                    // Выполняем перепаковку объектов
//                    for (c in categories) {
//                        val item = CategoryListResult.CategoryItem()
//                        item.id = c.id
//                        item.name = c.name
//                        item.imageLink = c.imageLink
//                        item.priority = c.priority
//                        item.receiptCount = c.receiptCount
//
//                        categoryItems.add(item)
//                    }
//                }
//
//                s.onNext(categoryItems)
//
//                s.onCompleted()
//            } catch (e: SQLException) {
//                s.onError(e)
//            }
//        }

        // Выполняем все запросы
//        Observable.concat(db, networkWithSave)
//                .compose(schedulersManager.applySchedulers<List<CategoryListResult.CategoryItem>?>())
//                .subscribeWith {
//                    onNext {
//                        Timber.i("Обновляем UI")
//
//                        refreshData(it)
//                    }
//
//                    onError {
//                        Timber.e(it, "Ошибка при загрузке категорий")
//
//                        if (activity != null) {
//                            progress.visibility = View.GONE
//
//                            if (adapter?.itemCount == 0) {
//                                list.visibility = View.GONE
//                                empty.text = "Во время загрузки данных произошла ошибка!"
//                                empty.visibility = View.VISIBLE
//                            } else {
//                                list.visibility = View.VISIBLE
//                                empty.visibility = View.GONE
//                            }
//                        }
//                    }
//                }
    }

    companion object {

        @JvmStatic
        fun newInstance(): CategoryListFragment {
            val args = Bundle()

            val fragment = CategoryListFragment()
            fragment.arguments = args

            return fragment
        }
    }
}
