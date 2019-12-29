package ru.ls.donkitchen.app

import androidx.multidex.MultiDexApplication

/**
 * Главный класс приложения
 */
open class DonKitchenApplication : MultiDexApplication() {
    private lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        instance = this
        createComponent()
        component.inject(this)
    }

    private fun createComponent(): AppComponent {
        component = DaggerAppComponent.builder().appModule(AppModule(this)).build()

        return component
    }

    fun component(): AppComponent {
        return component
    }

    companion object {
        private lateinit var instance: DonKitchenApplication

        @JvmStatic
        fun instance(): DonKitchenApplication {
            return instance
        }
    }
}
