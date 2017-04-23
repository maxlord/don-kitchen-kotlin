package ru.ls.donkitchen.ui.receiptdetail

import dagger.Subcomponent
import ru.ls.donkitchen.ui.receiptdetail.info.ReceiptDetailInfoPresenter
import ru.ls.donkitchen.annotation.PerScreen

@PerScreen
@Subcomponent(modules = arrayOf(ReceiptDetailModule::class))
interface ReceiptDetailSubComponent {
    fun inject(presenter: ReceiptDetailPresenter)
    fun inject(presenter: ReceiptDetailInfoPresenter)
}