package ru.ls.donkitchen.ui.categorylist

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_category_list.*
import ru.ls.donkitchen.R
import ru.ls.donkitchen.ui.receiptlist.ReceiptList
import ru.ls.donkitchen.app.DonKitchenApplication
import ru.ls.donkitchen.fragment.base.BaseFragment
import ru.ls.donkitchen.navigateActivity

class CategoryListFragment : BaseFragment(), CategoryListView {
    @InjectPresenter lateinit var presenter: CategoryListPresenter
    private var adapter: CategoryAdapter? = null

    @ProvidePresenter
    fun providePresenter() = CategoryListPresenter(
            DonKitchenApplication.instance().component().plus(CategoryListModule()))

    override fun getLayoutRes(): Int {
        return R.layout.fragment_category_list
    }

    override fun initControls(v: View?) {
        list.setHasFixedSize(true)

        adapter = CategoryAdapter(activity, object : CategoryAdapter.Callback {
            override fun onItemClick(item: CategoryViewItem) {
                navigateActivity<ReceiptList>(false, Bundle().apply {
                    putInt(ReceiptList.EXT_IN_CATEGORY_ID, item.id)
                    putString(ReceiptList.EXT_IN_CATEGORY_NAME, item.name)
                })
            }
        })
        list.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        list.adapter = adapter
    }

    override fun loadData() {

    }

    override fun showLoading() {
        progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
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
