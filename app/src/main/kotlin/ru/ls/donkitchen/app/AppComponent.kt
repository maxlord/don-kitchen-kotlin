package ru.ls.donkitchen.app

import dagger.Component
import ru.ls.donkitchen.activity.base.ActivityModule
import ru.ls.donkitchen.activity.base.ActivitySubComponent
import ru.ls.donkitchen.service.base.ServiceModule
import ru.ls.donkitchen.service.base.ServiceSubComponent
import ru.ls.donkitchen.ui.receiptdetail.ReceiptDetailActivityModule
import ru.ls.donkitchen.ui.receiptdetail.ReceiptDetailActivitySubComponent
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class)) interface AppComponent {
    fun inject(application: DonKitchenApplication)

    operator fun plus(module: ActivityModule): ActivitySubComponent
    operator fun plus(module: ServiceModule): ServiceSubComponent
//    operator fun plus(module: SplashModule): SplashSubComponent
    operator fun plus(module: ReceiptDetailActivityModule): ReceiptDetailActivitySubComponent
}
