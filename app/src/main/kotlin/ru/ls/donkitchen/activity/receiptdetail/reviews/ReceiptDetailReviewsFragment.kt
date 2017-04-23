package ru.ls.donkitchen.activity.receiptdetail.reviews

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_receipt_detail_reviews.*
import ru.ls.donkitchen.R
import ru.ls.donkitchen.activity.receiptdetail.ReceiptDetail
import ru.ls.donkitchen.app.DonKitchenApplication
import ru.ls.donkitchen.fragment.base.BaseFragment
import timber.log.Timber

class ReceiptDetailReviewsFragment : BaseFragment(), ReceiptReviewsView {
    @InjectPresenter lateinit var presenter: ReceiptReviewsPresenter
    private lateinit var adapter: ReviewAdapter

    @ProvidePresenter
    fun providePresenter(): ReceiptReviewsPresenter {
        val receiptId = arguments.getInt(ReceiptDetail.EXT_IN_RECEIPT_ID, 0)

        return ReceiptReviewsPresenter(receiptId,
                DonKitchenApplication.instance().component().plus(ReceiptReviewsModule()))
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_receipt_detail_reviews
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
        Timber.e("Ошибка при загрузке отзывов: $error")
        if (view != null) {
            Snackbar.make(view as View, error, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun displayReviews(reviews: List<ReviewViewItem>) {
        list.visibility = View.VISIBLE
        empty.visibility = View.GONE
        adapter.clear()
        adapter.addAllItems(reviews)
        adapter.notifyDataSetChanged()
    }

    override fun initControls(v: View?) {
        setHasOptionsMenu(true)
        list.setHasFixedSize(true)
        adapter = ReviewAdapter(activity)
        list.layoutManager = LinearLayoutManager(activity)
        list.adapter = adapter
    }

    override fun loadData() {
//        if (arguments != null) {
//            receiptId = arguments.getInt(ReceiptDetail.EXT_IN_RECEIPT_ID, 0)
//        }
    }
//
//    override fun onResume() {
//        super.onResume()
//
//        bus.register(this)
//
//        reloadData()
//    }

//    private fun refreshData(result: ReviewListResult?) {
//        try {
//            if (activity != null && result != null) {
//                progress!!.visibility = View.GONE
//
//                if (result.reviews?.isEmpty() == false) {
//                    adapter!!.clear()
//                    adapter!!.addAllItems(result.reviews!!)
//                    adapter!!.notifyDataSetChanged()
//
//                    list.visibility = View.VISIBLE
//                    empty!!.visibility = View.GONE
//                } else {
//                    list.visibility = View.GONE
//                    empty!!.visibility = View.VISIBLE
//                }
//            }
//        } catch (e: Exception) {
//            Timber.e(e, "Ошибка получения списка категорий")
//        }
//
//    }

    private fun reloadData() {
//        api.getReviewsByReceipt(receiptId)
//                .compose(schedulersManager.applySchedulers<ReviewListResult?>())
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
//                            progress!!.visibility = View.GONE
//
//                            if (adapter!!.itemCount == 0) {
//                                list.visibility = View.GONE
//                                empty!!.visibility = View.VISIBLE
//                            } else {
//                                list.visibility = View.VISIBLE
//                                empty!!.visibility = View.GONE
//                            }
//                        }
//                    }
//                }
    }

//    @Subscribe
//    fun onReload(event: ReviewAddedEvent) {
//        reloadData()
//    }

    companion object {
        @JvmStatic
        fun newInstance(receiptId: Int): ReceiptDetailReviewsFragment {
            val fragment = ReceiptDetailReviewsFragment()
            fragment.arguments = Bundle(1).apply {
                putInt(ReceiptDetail.EXT_IN_RECEIPT_ID, receiptId)
            }
            return fragment
        }
    }

}