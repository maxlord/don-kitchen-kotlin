package ru.ls.donkitchen.ui.receiptdetail.info

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ReceiptDetailInfoView: MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showLoading()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideLoading()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showImage(imageLink: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showViewsCount(viewsCount: Int)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showRating(ratingValue: Int)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showTitle(titleValue: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun bindIngredients(ingredientsValue: String?)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showReceipt(receiptValue: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun displayErrorLoadingDialog()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun leaveScreen()
}