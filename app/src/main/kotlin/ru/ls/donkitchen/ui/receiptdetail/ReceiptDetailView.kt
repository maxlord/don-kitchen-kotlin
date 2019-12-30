package ru.ls.donkitchen.ui.receiptdetail

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ReceiptDetailView: MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun initPager(receiptId: Int, receiptName: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setToolbarTitle(title: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun leaveScreen()

    @StateStrategyType(SkipStrategy::class)
    fun displayNewRatingDialog(receiptId: Int)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun displayShareReceipt(receiptContent: String)
}