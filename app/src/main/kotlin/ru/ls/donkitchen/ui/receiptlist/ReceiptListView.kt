package ru.ls.donkitchen.ui.receiptlist

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ReceiptListView: MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setToolbarTitle(title: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showLoading()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideLoading()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun displayNoData()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun displayError(error: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun displayReceipts(receipts: List<ReceiptViewItem>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun leaveScreen()
}