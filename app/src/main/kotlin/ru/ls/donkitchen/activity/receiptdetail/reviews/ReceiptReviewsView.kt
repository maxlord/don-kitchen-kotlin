package ru.ls.donkitchen.activity.receiptdetail.reviews

import com.arellomobile.mvp.MvpView

interface ReceiptReviewsView: MvpView {

    fun showLoading()

    fun hideLoading()

    fun displayNoData()

    fun displayError(error: String)

    fun displayReviews(reviews: List<ReviewViewItem>)

}