package ru.ls.donkitchen.ui.receiptdetail

import dagger.Subcomponent
import ru.ls.donkitchen.annotation.PerActivity

@PerActivity
@Subcomponent(modules = arrayOf(ReceiptDetailActivityModule::class))
interface ReceiptDetailActivitySubComponent {
    fun inject(activity: ReceiptDetail)

    operator fun plus(module: ReceiptDetailModule): ReceiptDetailSubComponent
}