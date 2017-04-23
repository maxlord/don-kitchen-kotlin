package ru.ls.donkitchen.activity.receiptdetail.info

import com.arellomobile.mvp.MvpView

interface ReceiptDetailInfoView: MvpView {

    fun showLoading()

    fun hideLoading()

    fun showImage(imageLink: String)

    fun showViewsCount(viewsCount: Int)

    fun showRating(ratingValue: Int)

    fun showTitle(titleValue: String)

    fun bindIngredients(ingredientsValue: String?)

    fun showReceipt(receiptValue: String)

    fun displayErrorLoadingDialog()

    fun leaveScreen()
}