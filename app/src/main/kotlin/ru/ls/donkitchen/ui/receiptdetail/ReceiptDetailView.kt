package ru.ls.donkitchen.ui.receiptdetail

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface ReceiptDetailView: MvpView {

    fun initPager(receiptId: Int, receiptName: String)

    fun setToolbarTitle(title: String)

    fun leaveScreen()

    @StateStrategyType(SkipStrategy::class)
    fun displayNewRatingDialog(receiptId: Int)

}