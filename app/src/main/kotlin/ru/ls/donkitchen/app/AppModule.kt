package ru.ls.donkitchen.app

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.j256.ormlite.android.apptools.OpenHelperManager
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.ls.donkitchen.BuildConfig
import ru.ls.donkitchen.activity.base.SchedulersFactory
import ru.ls.donkitchen.activity.base.SchedulersFactoryImpl
import ru.ls.donkitchen.data.rest.Api
import ru.ls.donkitchen.data.storage.ormlite.DatabaseHelper
import ru.ls.donkitchen.rest.converter.DateTimeDeserializer
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import timber.log.Timber
import java.util.*
import javax.inject.Singleton


@Module
class AppModule(private val application: DonKitchenApplication) {
    init {
        Timber.plant(Timber.DebugTree())
    }

    @Provides
    @Singleton fun provideApplication(): DonKitchenApplication {
        return application
    }

    @Provides
    @Singleton fun provideApi(gson: Gson): Api {
        val b = Retrofit.Builder()

        val clientBuilder = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.BASIC
        }
        clientBuilder.addInterceptor(interceptor)

        b.client(clientBuilder.build())
        b.addConverterFactory(GsonConverterFactory.create(gson))
        b.addCallAdapterFactory(RxJava2CallAdapterFactory.create())

        val retrofit = b.baseUrl(BuildConfig.API_URL).build()

        return retrofit.create(Api::class.java)
    }

    @Provides
    @Singleton fun provideGson(): Gson {
        return GsonBuilder()
                .registerTypeAdapter(Date::class.java, DateTimeDeserializer())
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .create()
    }

    @Provides @Singleton fun provideCicerone(): Cicerone<Router> {
        return Cicerone.create()
    }

    @Provides @Singleton fun provideNavigatorHolder(cicerone: Cicerone<Router>): NavigatorHolder {
        return cicerone.navigatorHolder
    }

    @Provides @Singleton fun provideNavigatorRouter(cicerone: Cicerone<Router>): Router {
        return cicerone.router
    }

    @Provides @Singleton fun provideSchedulersFactory(): SchedulersFactory {
        return SchedulersFactoryImpl()
    }

    @Provides @Singleton fun provideDatabaseHelper(): DatabaseHelper {
        return OpenHelperManager.getHelper(application, DatabaseHelper::class.java)
    }
}
