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

class CategoryListFragment : BaseFragment(), CategoryListView {
    @InjectPresenter lateinit var presenter: CategoryListPresenter
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
        presenter.start()
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun displayError(error: String) {
        if (view != null) {
            Snackbar.make(view as View, error, Snackbar.LENGTH_SHORT).show()
        }
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
