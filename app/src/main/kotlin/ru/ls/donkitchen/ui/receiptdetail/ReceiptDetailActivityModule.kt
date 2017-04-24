package ru.ls.donkitchen.ui.receiptdetail

import com.google.firebase.analytics.FirebaseAnalytics
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

    @Provides
    @PerActivity
    fun provideFirebaseAnalytics(): FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(activity)
    }

}