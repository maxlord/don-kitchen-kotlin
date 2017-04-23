package ru.ls.donkitchen.ui.receiptlist

import dagger.Subcomponent
import ru.ls.donkitchen.annotation.PerScreen

@PerScreen
@Subcomponent(modules = arrayOf(ReceiptListModule::class))
interface ReceiptListSubComponent {
    fun inject(presenter: ReceiptListPresenter)
}
