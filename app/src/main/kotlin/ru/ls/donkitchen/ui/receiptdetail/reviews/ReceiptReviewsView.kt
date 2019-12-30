package ru.ls.donkitchen.ui.receiptdetail.reviews

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ReceiptReviewsView: MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showLoading()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideLoading()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun displayNoData()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun displayError(error: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun displayReviews(reviews: List<ReviewViewItem>)
}