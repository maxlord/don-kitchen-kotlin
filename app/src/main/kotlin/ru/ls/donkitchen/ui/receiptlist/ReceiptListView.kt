package ru.ls.donkitchen.ui.receiptlist

import com.arellomobile.mvp.MvpView

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 16.04.17
 */
interface ReceiptListView: MvpView {
    fun setToolbarTitle(title: String)

    fun showLoading()

    fun hideLoading()

    fun displayNoData()

    fun displayError(error: String)

    fun displayReceipts(receipts: List<ReceiptViewItem>)

    fun leaveScreen()
}