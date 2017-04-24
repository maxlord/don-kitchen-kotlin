package ru.ls.donkitchen.activity.base

import dagger.Subcomponent
import ru.ls.donkitchen.annotation.PerActivity
import ru.ls.donkitchen.fragment.base.FragmentModule
import ru.ls.donkitchen.fragment.base.FragmentSubComponent
import ru.ls.donkitchen.ui.categorylist.CategoryListModule
import ru.ls.donkitchen.ui.categorylist.CategoryListSubComponent
import ru.ls.donkitchen.ui.receiptdetail.ReceiptDetailActivityModule
import ru.ls.donkitchen.ui.receiptdetail.ReceiptDetailActivitySubComponent
import ru.ls.donkitchen.ui.receiptlist.ReceiptListModule
import ru.ls.donkitchen.ui.receiptlist.ReceiptListSubComponent
import ru.ls.donkitchen.ui.splash.SplashModule
import ru.ls.donkitchen.ui.splash.SplashSubComponent

@PerActivity
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivitySubComponent {
//    @ConfigPrefs fun provideConfigPrefs(): SharedPreferences
//    fun provideSchedulersFactory(): SchedulersFactory

    operator fun plus(module: FragmentModule): FragmentSubComponent
    operator fun plus(module: SplashModule): SplashSubComponent
    operator fun plus(module: ReceiptListModule): ReceiptListSubComponent
    operator fun plus(module: CategoryListModule): CategoryListSubComponent

    fun inject(activity: BaseActivity)
    fun inject(activity: BaseNoActionBarActivity)
}
