package ru.ls.donkitchen.service.base

import android.app.Service
import dagger.Module
import dagger.Provides
import ru.ls.donkitchen.annotation.PerService

@Module
class ServiceModule(private val service: Service) {
    @Provides
    @PerService
    fun provideService(): Service {
        return service
    }

//    @Provides
//    @PerService
//    fun provideDatabaseHelper(): DatabaseHelper {
//        return OpenHelperManager.getHelper(service, DatabaseHelper::class.java)
//    }

//    @Provides
//    @PerService
//    @ConfigPrefs
//    fun provideConfigPrefs(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(service.applicationContext)
}
