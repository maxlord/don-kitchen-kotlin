package ru.ls.donkitchen.ui.receiptdetail.review

import androidx.annotation.StringRes
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ReviewView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showTitle(@StringRes titleId: Int)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showRating(ratingValue: Float)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun dismissDialog()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun displayReviewSuccessMessage()
}