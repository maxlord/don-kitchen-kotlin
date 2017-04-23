package ru.ls.donkitchen.activity.base

import dagger.Subcomponent
import ru.ls.donkitchen.annotation.PerActivity
import ru.ls.donkitchen.fragment.base.FragmentModule
import ru.ls.donkitchen.fragment.base.FragmentSubComponent
import ru.ls.donkitchen.ui.splash.SplashModule
import ru.ls.donkitchen.ui.splash.SplashSubComponent

@PerActivity
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivitySubComponent {
//    @ConfigPrefs fun provideConfigPrefs(): SharedPreferences
//    fun provideBus(): Bus
//    fun provideSchedulersFactory(): SchedulersFactory

    operator fun plus(module: FragmentModule): FragmentSubComponent
    operator fun plus(module: SplashModule): SplashSubComponent

    fun inject(activity: BaseActivity)
    fun inject(activity: BaseNoActionBarActivity)
}
