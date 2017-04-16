package ru.ls.donkitchen.app

import android.support.multidex.MultiDexApplication

/**
 * Главный класс приложения
 */
open class DonKitchenApplication : MultiDexApplication() {

    lateinit var component: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        createComponent()
        component.inject(this)
    }

    open fun createComponent(): AppComponent {
        component = DaggerAppComponent.builder().appModule(AppModule(this)).build()

        return component
    }

    fun component(): AppComponent {
        return component
    }
}
