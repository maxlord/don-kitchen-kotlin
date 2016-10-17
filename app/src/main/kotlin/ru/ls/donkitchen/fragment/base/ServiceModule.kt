package ru.ls.donkitchen.fragment.base

import android.app.Service
import dagger.Module
import dagger.Provides
import ru.ls.donkitchen.annotation.PerService

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 15.04.16
 */
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
