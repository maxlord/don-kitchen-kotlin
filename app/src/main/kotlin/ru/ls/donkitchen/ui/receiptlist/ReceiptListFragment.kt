package ru.ls.donkitchen.ui.receiptlist

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_receipt_list.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.appcompat.v7.navigationIconResource
import ru.ls.donkitchen.R
import ru.ls.donkitchen.activity.base.BaseNoActionBarActivity
import ru.ls.donkitchen.fragment.base.BaseFragment
import timber.log.Timber

class ReceiptListFragment : BaseFragment(), ReceiptListView {
    @InjectPresenter lateinit var presenter: ReceiptListPresenter
    private lateinit var adapter: ReceiptAdapter

    @ProvidePresenter
    fun providePresenter(): ReceiptListPresenter {
        val categoryId = arguments?.getInt(ReceiptList.EXT_IN_CATEGORY_ID, 0) ?: 0
        val categoryName = arguments?.getString(ReceiptList.EXT_IN_CATEGORY_NAME) ?: ""

        return ReceiptListPresenter(categoryId, categoryName,
                (activity as BaseNoActionBarActivity).component().plus(ReceiptListModule()))
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_receipt_list
    }

    override fun initControls(v: View?) {
        setHasOptionsMenu(true)

        val recycledViewPool = RecyclerView.RecycledViewPool()
        recycledViewPool.setMaxRecycledViews(1, 10)
        list.recycledViewPool = recycledViewPool
        list.setHasFixedSize(true)

        adapter = ReceiptAdapter(activity!!, object : ReceiptAdapter.Callback {
            override fun onItemClick(item: ReceiptViewItem) {
                presenter.onReceiptClick(item.id, item.name)
            }
        })
        list.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        list.adapter = adapter
        toolbar?.navigationIconResource = R.drawable.ic_arrow_back
    }

    override fun loadData() {
        presenter.upClicks(upClicks())
    }

    private fun upClicks(): Observable<Unit> {
        return Observable.create<Unit> { emitter ->
            emitter.setCancellable { toolbar?.setNavigationOnClickListener(null) }
            toolbar?.setNavigationOnClickListener {
                emitter.onNext(Unit)
            }
        }
    }

    override fun setToolbarTitle(title: String) {
        toolbar?.title = title
    }

    override fun showLoading() {
        progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
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
        activity?.onBackPressed()
    }

    companion object {
        @JvmStatic
        fun newInstance(categoryId: Int, categoryName: String): ReceiptListFragment {
            val args = Bundle()
            args.putInt(ReceiptList.EXT_IN_CATEGORY_ID, categoryId)
            args.putString(ReceiptList.EXT_IN_CATEGORY_NAME, categoryName)

            val fragment = ReceiptListFragment()
            fragment.arguments = args

            return fragment
        }
    }
}
