package ru.ls.donkitchen.service.base

import android.app.Service
import com.j256.ormlite.android.apptools.OpenHelperManager
import dagger.Module
import dagger.Provides
import ru.ls.donkitchen.annotation.PerService
import ru.ls.donkitchen.db.DatabaseHelper

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

    @Provides
    @PerService
    fun provideDatabaseHelper(): DatabaseHelper {
        return OpenHelperManager.getHelper(service, DatabaseHelper::class.java)
    }

//    @Provides
//    @PerService
//    @ConfigPrefs
//    fun provideConfigPrefs(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(service.applicationContext)
}
