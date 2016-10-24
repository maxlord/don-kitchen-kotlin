package ru.ls.donkitchen.app

import dagger.Component
import ru.ls.donkitchen.activity.base.ActivityModule
import ru.ls.donkitchen.activity.base.ActivitySubComponent
import ru.ls.donkitchen.annotation.IOSched
import ru.ls.donkitchen.annotation.UISched
import ru.ls.donkitchen.service.base.ServiceModule
import ru.ls.donkitchen.service.base.ServiceSubComponent
import ru.ls.donkitchen.rest.Api
import rx.Scheduler
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class)) interface AppComponent {
    fun inject(application: DonKitchenApplication)

    fun provideApplication(): DonKitchenApplication
    fun provideApi(): Api
//    fun provideMockApi(): MockApi

    @IOSched fun provideIoScheduler(): Scheduler

    @UISched fun provideUiScheduler(): Scheduler

    operator fun plus(module: ActivityModule): ActivitySubComponent
    operator fun plus(module: ServiceModule): ServiceSubComponent
}
