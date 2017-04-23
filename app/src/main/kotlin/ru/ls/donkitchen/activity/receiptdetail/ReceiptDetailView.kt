package ru.ls.donkitchen.activity.receiptdetail

import com.arellomobile.mvp.MvpView

interface ReceiptDetailView: MvpView {
    fun initPager(receiptId: Int)
    fun setToolbarTitle(title: String)
    fun leaveScreen()
}