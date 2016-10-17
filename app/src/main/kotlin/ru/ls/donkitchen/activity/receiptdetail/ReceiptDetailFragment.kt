package ru.ls.donkitchen.activity.receiptdetail

import android.app.Fragment
import android.os.Bundle
import android.support.v13.app.FragmentPagerAdapter
import android.support.v7.app.AlertDialog
import android.view.View
import com.squareup.otto.Bus
import com.squareup.otto.Subscribe
import kotlinx.android.synthetic.main.dialog_new_review.view.*
import kotlinx.android.synthetic.main.fragment_receipt_detail.*
import kotlinx.android.synthetic.main.widget_toolbar.*
import org.jetbrains.anko.appcompat.v7.navigationIconResource
import ru.ls.donkitchen.BuildConfig
import ru.ls.donkitchen.R
import ru.ls.donkitchen.activity.base.BaseActivity
import ru.ls.donkitchen.activity.base.SchedulersManager
import ru.ls.donkitchen.activity.receiptdetail.event.AddReviewEvent
import ru.ls.donkitchen.activity.receiptdetail.event.ReviewAddedEvent
import ru.ls.donkitchen.app.DonKitchenApplication
import ru.ls.donkitchen.db.DatabaseHelper
import ru.ls.donkitchen.fragment.base.BaseFragment
import ru.ls.donkitchen.rest.Api
import ru.ls.donkitchen.rest.model.request.ReceiptIncrementViews
import ru.ls.donkitchen.rest.model.response.ReceiptDetailResult
import ru.ls.donkitchen.rest.model.response.ReviewResult
import rx.lang.kotlin.subscribeWith
import timber.log.Timber
import javax.inject.Inject

/**
 * Просмотр рецепта
 *
 * @author Lord (Kuleshov M.V.)
 * @since 17.10.16
 */
class ReceiptDetailFragment : BaseFragment() {
    @Inject lateinit var application: DonKitchenApplication
    @Inject lateinit var activity: BaseActivity
    @Inject lateinit var api: Api
    @Inject lateinit var schedulersManager: SchedulersManager
    @Inject lateinit var bus: Bus
    @Inject lateinit var databaseHelper: DatabaseHelper

    private var receiptId: Int = 0
    private var receiptName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onPause() {
        super.onPause()

        bus.unregister(this)
    }

    override fun onResume() {
        super.onResume()

        bus.register(this)
    }

    @Subscribe
    fun eventAddReview(event: AddReviewEvent) {
        addReview()
    }

    private fun addReview() {
        val dv = activity.layoutInflater.inflate(R.layout.dialog_new_review, null)

        AlertDialog.Builder(activity)
                .setTitle(R.string.activity_receipt_detail_dialog_add_review_title)
                .setView(dv)
                .setPositiveButton(R.string.common_ok, { dialog, which ->
                    val rating = dv.rating_bar.rating.toInt()

                    var uname = ""
                    if (!dv.user_name.text.toString().isNullOrBlank()) {
                        uname = dv.user_name.text.toString()
                    }

                    var comment = ""
                    if (!dv.comments.text.toString().isNullOrBlank()) {
                        comment = dv.comments.text.toString()
                    }

                    api.addReview(receiptId, rating, uname, comment)
                            .compose(schedulersManager.applySchedulers<ReviewResult>())
                            .subscribeWith {
                                onNext {
                                    if (it != null) {
                                        bus.post(ReviewAddedEvent())

                                        AlertDialog.Builder(activity)
                                                .setTitle(R.string.activity_receipt_detail_dialog_title_review_added_success)
                                                .setMessage(R.string.activity_receipt_detail_dialog_review_added_success)
                                                .setPositiveButton(R.string.common_ok, null)
                                                .create()
                                                .show()
                                    }
                                }
                            }
                })
                .setNegativeButton(R.string.common_cancel, null)
                .create()
                .show()
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_receipt_detail
    }

    override fun inject() {
        getComponent().inject(this)
    }

    override fun initControls(v: View?) {
        super.initControls(v)

        toolbar?.navigationIconResource = R.drawable.ic_arrow_back
        toolbar?.setNavigationOnClickListener { activity.onBackPressed() }
        toolbar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_rate -> addReview()
            }

            true
        }
        toolbar?.inflateMenu(R.menu.menu_receipt_detail)
    }

    override fun loadData() {
        pager.adapter = object : FragmentPagerAdapter(fragmentManager) {
            override fun getItem(position: Int): Fragment? {
                when (position) {
                    0 -> return ReceiptDetailInfoFragment.newInstance(receiptId)
                    1 -> return ReceiptDetailReviewsFragment.newInstance(receiptId)
                }

                return null
            }

            override fun getPageTitle(position: Int): CharSequence {
                when (position) {
                    0 -> return resources.getString(R.string.activity_receipt_detail_tab_info)

                    1 -> return resources.getString(R.string.activity_receipt_detail_tab_reviews)
                }

                return super.getPageTitle(position)
            }

            override fun getCount(): Int {
                return 2
            }
        }

        tablayout.setupWithViewPager(pager)

        if (arguments != null) {
            receiptId = arguments.getInt(ReceiptDetail.EXT_IN_RECEIPT_ID, 0)
            receiptName = arguments.getString(ReceiptDetail.EXT_IN_RECEIPT_NAME)

            toolbar?.title = receiptName

            if (receiptId > 0) {
                // Увеличиваем счетчик просмотров
                if (!BuildConfig.DEBUG) {
                    api.incrementReceiptViews(receiptId, ReceiptIncrementViews())
                            .compose(schedulersManager.applySchedulers<ReceiptDetailResult>())
                            .subscribeWith {
                                onNext {

                                }

                                onError {
                                    Timber.e(it, "Ошибка увеличения количества просмотров")
                                }
                            }
                }
            }
        }
    }

    companion object {

        fun newInstance(receiptId: Int, receiptName: String): ReceiptDetailFragment {
            val args = Bundle()
            args.putInt(ReceiptDetail.EXT_IN_RECEIPT_ID, receiptId)
            args.putString(ReceiptDetail.EXT_IN_RECEIPT_NAME, receiptName)

            val fragment = ReceiptDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
