package ru.ls.donkitchen.activity.receiptlist

import dagger.Subcomponent
import ru.ls.donkitchen.annotation.PerScreen

@PerScreen
@Subcomponent(modules = arrayOf(ReceiptListModule::class))
interface ReceiptListSubComponent {
    fun inject(presenter: ReceiptListPresenter)
}
