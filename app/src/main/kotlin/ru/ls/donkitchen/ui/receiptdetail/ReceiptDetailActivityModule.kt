package ru.ls.donkitchen.ui.receiptdetail

import dagger.Module
import dagger.Provides
import ru.ls.donkitchen.annotation.PerActivity

@Module
class ReceiptDetailActivityModule(private val activity: ReceiptDetail) {

    @PerActivity
    @Provides
    fun provideBus(): RxBus {
        return RxBus()
    }

}