package ru.ls.donkitchen.activity.receiptlist

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_receipt_list.*
import kotlinx.android.synthetic.main.widget_toolbar.*
import org.jetbrains.anko.appcompat.v7.navigationIconResource
import ru.ls.donkitchen.R
import ru.ls.donkitchen.activity.receiptdetail.ReceiptDetail
import ru.ls.donkitchen.fragment.base.BaseFragment
import ru.ls.donkitchen.navigateActivity
import timber.log.Timber

class ReceiptListFragment : BaseFragment(), ReceiptListView {
    @InjectPresenter lateinit var presenter: ReceiptListPresenter
    private lateinit var adapter: ReceiptAdapter

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
        getComponent().plus(ReceiptListModule()).inject(presenter)
    }

    override fun initControls(v: View?) {
        setHasOptionsMenu(true)

        val recycledViewPool = RecyclerView.RecycledViewPool()
        recycledViewPool.setMaxRecycledViews(1, 10)
        list.recycledViewPool = recycledViewPool
        list.setHasFixedSize(true)

        adapter = ReceiptAdapter(activity, object : ReceiptAdapter.Callback {
            override fun onItemClick(item: ReceiptViewItem) {
                val b = Bundle()
                b.putInt(ReceiptDetail.EXT_IN_RECEIPT_ID, item.id)
                b.putString(ReceiptDetail.EXT_IN_RECEIPT_NAME, item.name)
                b.putString(ReceiptDetail.EXT_IN_RECEIPT_PHOTO_URL, item.imageLink)

                navigateActivity<ReceiptDetail>(false, b)
            }
        })
        list.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        list.adapter = adapter
        toolbar?.navigationIconResource = R.drawable.ic_arrow_back
        toolbar?.setNavigationOnClickListener {
            presenter.onBackAction()
        }
    }

    override fun loadData() {
        arguments ?: return

        with(arguments) {
            presenter.start(
                    categoryId = getInt(ReceiptList.EXT_IN_CATEGORY_ID, 0),
                    categoryName = getString(ReceiptList.EXT_IN_CATEGORY_NAME))
        }
    }

    override fun setToolbarTitle(title: String) {
        toolbar?.title = title
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun displayNoData() {
        list.visibility = View.GONE
        empty.visibility = View.VISIBLE
    }

    override fun displayError(error: String) {
        Timber.e("Ошибка при загрузке рецептов: $error")
        if (view != null) {
            Snackbar.make(view as View, error, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun displayReceipts(receipts: List<ReceiptViewItem>) {
        list.visibility = View.VISIBLE
        empty.visibility = View.GONE

        adapter.clear()
        adapter.addAllItems(receipts)
        adapter.notifyDataSetChanged()
    }

    override fun leaveScreen() {
        activity.onBackPressed()
    }
}