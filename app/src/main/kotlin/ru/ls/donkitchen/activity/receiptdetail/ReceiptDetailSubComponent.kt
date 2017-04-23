package ru.ls.donkitchen.activity.receiptdetail

import dagger.Subcomponent
import ru.ls.donkitchen.activity.receiptdetail.info.ReceiptDetailInfoPresenter
import ru.ls.donkitchen.annotation.PerScreen

@PerScreen
@Subcomponent(modules = arrayOf(ReceiptDetailModule::class))
interface ReceiptDetailSubComponent {
    fun inject(presenter: ReceiptDetailPresenter)
    fun inject(presenter: ReceiptDetailInfoPresenter)
}