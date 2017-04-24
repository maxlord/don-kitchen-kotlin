package ru.ls.donkitchen.ui.receiptdetail.info

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Single
import jp.wasabeef.glide.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.fragment_receipt_detail_info.*
import ru.ls.donkitchen.R
import ru.ls.donkitchen.fragment.base.BaseFragment
import ru.ls.donkitchen.ui.receiptdetail.ReceiptDetail
import ru.ls.donkitchen.ui.receiptdetail.ReceiptDetailModule

class ReceiptDetailInfoFragment : BaseFragment(), ReceiptDetailInfoView {
    @InjectPresenter lateinit var presenter: ReceiptDetailInfoPresenter

    @ProvidePresenter
    fun providePresenter(): ReceiptDetailInfoPresenter {
        val receiptId = arguments.getInt(ReceiptDetail.EXT_IN_RECEIPT_ID, 0)

        return ReceiptDetailInfoPresenter(receiptId,
                (activity as ReceiptDetail).component().plus(ReceiptDetailModule()))
    }

    override fun showLoading() {
        progress.visibility = View.VISIBLE
        container.visibility = View.GONE
    }

    override fun hideLoading() {
        progress.visibility = View.GONE
        container.visibility = View.VISIBLE
    }

    override fun showImage(imageLink: String) {
        Glide.with(this)
                .load(imageLink)
                .bitmapTransform(CropCircleTransformation(context))
                .fitCenter()
                .centerCrop()
                .into(icon)
    }

    override fun showViewsCount(viewsCount: Int) {
        views.text = "$viewsCount"
    }

    override fun showRating(ratingValue: Int) {
        rating.rating = ratingValue.toFloat()
    }

    override fun showTitle(titleValue: String) {
        title.text = titleValue
    }

    override fun bindIngredients(ingredientsValue: String?) {
        if (!ingredientsValue.isNullOrEmpty()) {
            ingredients.text = ingredientsValue
            container_ingredients.visibility = View.VISIBLE
        } else {
            container_ingredients.visibility = View.GONE
        }
    }

    override fun showReceipt(receiptValue: String) {
        receipt.text = receiptValue
    }

    override fun displayErrorLoadingDialog() {
        presenter.errorLoadingDialogClicks(errorLoadingDialogClicks())
    }

    private fun errorLoadingDialogClicks(): Single<Unit> {
        return Single.create { emitter ->
            AlertDialog.Builder(activity)
                    .setTitle(R.string.common_info)
                    .setMessage(R.string.activity_receipt_detail_dialog_error_loading_receipt)
                    .setPositiveButton(R.string.common_ok, { _, _ ->
                        emitter.onSuccess(Unit)
                    })
                    .setCancelable(false)
                    .create()
                    .show()
        }
    }

    override fun leaveScreen() {
        activity.finish()
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_receipt_detail_info
    }

    override fun initControls(v: View?) {
        super.initControls(v)

        presenter.rateClicks(rate_button.clicks())
    }

    companion object {
        @JvmStatic
        fun newInstance(receiptId: Int): ReceiptDetailInfoFragment {
            val fragment = ReceiptDetailInfoFragment()
            fragment.arguments = Bundle(1).apply {
                putInt(ReceiptDetail.EXT_IN_RECEIPT_ID, receiptId)
            }
            return fragment
        }
    }
}
