package ru.ls.donkitchen.app

import dagger.Component
import ru.ls.donkitchen.activity.base.ActivityModule
import ru.ls.donkitchen.activity.base.ActivitySubComponent
import ru.ls.donkitchen.activity.splash.SplashModule
import ru.ls.donkitchen.activity.splash.SplashSubComponent
import ru.ls.donkitchen.service.base.ServiceModule
import ru.ls.donkitchen.service.base.ServiceSubComponent
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class)) interface AppComponent {
    fun inject(application: DonKitchenApplication)

//    fun provideApplication(): DonKitchenApplication
//    fun provideApi(): Api
//    fun provideCicerone(): Cicerone<Router>
//    fun provideNavigatorHolder(): NavigatorHolder
//    fun provideRouter(): Router
//
//    fun provideSchedulersManager(): SchedulersManager
//    fun provideSchedulersFactory(): SchedulersFactory
//    @IOSched fun provideIoScheduler(): Scheduler
//    @UISched fun provideUiScheduler(): Scheduler

    operator fun plus(module: ActivityModule): ActivitySubComponent
    operator fun plus(module: ServiceModule): ServiceSubComponent

    operator fun plus(module: SplashModule): SplashSubComponent
}
