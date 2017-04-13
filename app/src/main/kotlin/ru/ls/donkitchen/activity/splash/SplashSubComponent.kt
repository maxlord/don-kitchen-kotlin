package ru.ls.donkitchen.activity.splash

import dagger.Subcomponent
import ru.ls.donkitchen.annotation.PerScreen

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 02.04.17
 */
@PerScreen
@Subcomponent(modules = arrayOf(SplashModule::class))
interface SplashSubComponent {
    fun inject(activity: Splash)
    fun inject(presenter: SplashPresenter)
}
