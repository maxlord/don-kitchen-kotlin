package ru.ls.donkitchen.activity.receiptdetail

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.squareup.otto.Bus
import com.squareup.otto.Subscribe
import kotlinx.android.synthetic.main.fragment_receipt_detail_reviews.*
import ru.ls.donkitchen.R
import ru.ls.donkitchen.activity.base.SchedulersManager
import ru.ls.donkitchen.activity.receiptdetail.event.ReviewAddedEvent
import ru.ls.donkitchen.app.DonKitchenApplication
import ru.ls.donkitchen.db.DatabaseHelper
import ru.ls.donkitchen.fragment.base.BaseFragment
import ru.ls.donkitchen.rest.Api
import ru.ls.donkitchen.rest.model.response.ReviewListResult
import rx.lang.kotlin.subscribeWith
import timber.log.Timber
import javax.inject.Inject

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 17.10.16
 */
class ReceiptDetailReviewsFragment : BaseFragment() {
    @Inject lateinit var application: DonKitchenApplication
    @Inject lateinit var api: Api
    @Inject lateinit var schedulersManager: SchedulersManager
    @Inject lateinit var bus: Bus
    @Inject lateinit var databaseHelper: DatabaseHelper

    internal var receiptId: Int = 0
    private var adapter: ReviewAdapter? = null

    override fun onPause() {
        super.onPause()

        bus.unregister(this)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_receipt_detail_reviews
    }

    override fun inject() {
        getComponent().inject(this)
    }

    override fun initControls(v: View?) {
        setHasOptionsMenu(true)

        list.setHasFixedSize(true)

        adapter = ReviewAdapter(activity)
        list.layoutManager = LinearLayoutManager(activity)
        list.adapter = adapter
    }

    override fun loadData() {
        if (arguments != null) {
            receiptId = arguments.getInt(ReceiptDetail.EXT_IN_RECEIPT_ID, 0)
        }
    }

    override fun onResume() {
        super.onResume()

        bus.register(this)
        
        reloadData()
    }

    private fun refreshData(result: ReviewListResult?) {
        try {
            if (activity != null && result != null) {
                progress!!.visibility = View.GONE

                if (result.reviews?.isEmpty() == false) {
                    adapter!!.clear()
                    adapter!!.addAllItems(result.reviews!!)
                    adapter!!.notifyDataSetChanged()

                    list.visibility = View.VISIBLE
                    empty!!.visibility = View.GONE
                } else {
                    list.visibility = View.GONE
                    empty!!.visibility = View.VISIBLE
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Ошибка получения списка категорий")
        }

    }

    private fun reloadData() {
        api.getReviewsByReceipt(receiptId)
                .compose(schedulersManager.applySchedulers<ReviewListResult?>())
                .subscribeWith {
                    onNext {
                        Timber.i("Обновляем UI")

                        refreshData(it)
                    }

                    onError {
                        Timber.e(it, "Ошибка при загрузке категорий")

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

    @Subscribe
    fun onReload(event: ReviewAddedEvent) {
        reloadData()
    }

    companion object {

        fun newInstance(receiptId: Int): ReceiptDetailReviewsFragment {
            val args = Bundle()

            args.putInt(ReceiptDetail.EXT_IN_RECEIPT_ID, receiptId)

            val fragment = ReceiptDetailReviewsFragment()
            fragment.arguments = args

            return fragment
        }
    }
}
