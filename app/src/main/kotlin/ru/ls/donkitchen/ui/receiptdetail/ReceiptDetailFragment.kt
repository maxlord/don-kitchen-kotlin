package ru.ls.donkitchen.ui.receiptdetail

import android.os.Bundle
import android.support.v4.app.FragmentPagerAdapter
import android.view.View
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_receipt_detail.*
import kotlinx.android.synthetic.main.widget_toolbar.*
import ru.ls.donkitchen.R
import ru.ls.donkitchen.fragment.base.BaseFragment
import ru.ls.donkitchen.ui.receiptdetail.info.ReceiptDetailInfoFragment
import ru.ls.donkitchen.ui.receiptdetail.review.ReviewDialogFragment
import ru.ls.donkitchen.ui.receiptdetail.reviews.ReceiptDetailReviewsFragment

/**
 * Просмотр рецепта
 *
 * @author Lord (Kuleshov M.V.)
 * @since 17.10.16
 */
class ReceiptDetailFragment : BaseFragment(), ReceiptDetailView {

    @InjectPresenter lateinit var presenter: ReceiptDetailPresenter

    @ProvidePresenter
    fun providePresenter(): ReceiptDetailPresenter {
        val receiptId = arguments.getInt(ReceiptDetail.EXT_IN_RECEIPT_ID, 0)
        return ReceiptDetailPresenter(receiptId,
                (activity as ReceiptDetail).component().plus(ReceiptDetailModule()))
    }

    override fun initPager(receiptId: Int) {
        pager.adapter = object : FragmentPagerAdapter(fragmentManager) {
            override fun getItem(position: Int): MvpAppCompatFragment? {
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
    }

    override fun setToolbarTitle(title: String) {
        toolbar?.title = title
    }

    override fun displayNewRatingDialog(receiptId: Int) {
        ReviewDialogFragment.newInstance(receiptId).show(fragmentManager, "review_dialog")
    }

    override fun leaveScreen() = activity.finish()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_receipt_detail
    }

    override fun initControls(v: View?) {
        super.initControls(v)

        toolbar?.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar?.inflateMenu(R.menu.menu_receipt_detail)
    }

    override fun loadData() {
        presenter.upClicks(upClicks())
        presenter.toolbarClicks(toolbarClicks())
    }

    private fun upClicks(): Observable<Unit> {
        return Observable.create<Unit> { emitter ->
            emitter.setCancellable { toolbar?.setNavigationOnClickListener(null) }
            toolbar?.setNavigationOnClickListener {
                emitter.onNext(Unit)
            }
        }
    }

    private fun toolbarClicks(): Observable<Int> {
        return Observable.create<Int> { emitter ->
            emitter.setCancellable { toolbar?.setOnMenuItemClickListener(null) }
            toolbar?.setOnMenuItemClickListener { it ->
                emitter.onNext(it.itemId)
                true
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(receiptId: Int, receiptName: String): ReceiptDetailFragment {
            val fragment = ReceiptDetailFragment()
            fragment.arguments = Bundle(2).apply {
                putInt(ReceiptDetail.EXT_IN_RECEIPT_ID, receiptId)
                putString(ReceiptDetail.EXT_IN_RECEIPT_NAME, receiptName)
            }
            return fragment
        }
    }

}