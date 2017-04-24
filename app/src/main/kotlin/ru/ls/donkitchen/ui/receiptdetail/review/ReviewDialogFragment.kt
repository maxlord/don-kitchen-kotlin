package ru.ls.donkitchen.ui.receiptdetail.review

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.RxRatingBar
import com.jakewharton.rxbinding2.widget.textChanges
import kotlinx.android.synthetic.main.dialog_new_review.*
import ru.ls.donkitchen.R
import ru.ls.donkitchen.fragment.base.BaseDialogFragment
import ru.ls.donkitchen.ui.receiptdetail.ReceiptDetail
import ru.ls.donkitchen.ui.receiptdetail.ReceiptDetailModule

class ReviewDialogFragment : BaseDialogFragment(), ReviewView {
    @InjectPresenter lateinit var presenter: ReviewPresenter

    @ProvidePresenter
    fun providePresenter(): ReviewPresenter {
        val receiptId = arguments.getInt(ReceiptDetail.EXT_IN_RECEIPT_ID, 0)

        return ReviewPresenter(receiptId,
                (activity as ReceiptDetail).component().plus(ReceiptDetailModule()))
    }

    override fun getLayoutRes() = R.layout.dialog_new_review

    override fun loadData() {
        presenter.ratingChanges(RxRatingBar.ratingChangeEvents(rating_bar))
        presenter.userNameChanges(user_name.textChanges())
        presenter.commentChanges(comments.textChanges())
        presenter.positiveClicks(ok_button.clicks())
        presenter.negativeClicks(cancel_button.clicks())
    }

    override fun showTitle(@StringRes titleId: Int) {
        dialog.setTitle(titleId)
    }

    override fun showRating(ratingValue: Float) {
        rating_bar.rating = ratingValue
    }

    override fun dismissDialog() {
        dismissAllowingStateLoss()
    }

    override fun displayReviewSuccessMessage() {
        AlertDialog.Builder(activity)
                .setTitle(R.string.activity_receipt_detail_dialog_title_review_added_success)
                .setMessage(R.string.activity_receipt_detail_dialog_review_added_success)
                .setPositiveButton(R.string.common_ok, null)
                .create()
                .show()
    }

    companion object {
        @JvmStatic
        fun newInstance(receiptId: Int): ReviewDialogFragment {
            val fragment = ReviewDialogFragment()
            fragment.arguments = Bundle(1).apply {
                putInt(ReceiptDetail.EXT_IN_RECEIPT_ID, receiptId)
            }
            return fragment
        }
    }
}