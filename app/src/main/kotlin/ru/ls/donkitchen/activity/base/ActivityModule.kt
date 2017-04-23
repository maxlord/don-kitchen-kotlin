package ru.ls.donkitchen.activity.base

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.arellomobile.mvp.MvpAppCompatActivity
import com.squareup.otto.Bus
import dagger.Module
import dagger.Provides
import ru.ls.donkitchen.annotation.ConfigPrefs
import ru.ls.donkitchen.annotation.PerActivity
import ru.ls.donkitchen.util.AsyncBus
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 *
 * @author Lord (Kuleshov M.V.)
 * @since 11.01.16
 */
@Module
class ActivityModule(private val activity: MvpAppCompatActivity) {
    @Provides
    @PerActivity
    fun provideContext(): Context {
        return activity
    }

    @Provides
    @PerActivity
    fun provideActivity(): MvpAppCompatActivity {
        return activity
    }

    @Provides
    @PerActivity
    fun provideBaseActivity(): BaseActivity {
        return activity as BaseActivity
    }

    @Provides
    @ConfigPrefs
    @PerActivity
    fun provideConfigPrefs(): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(activity)
    }

    @Provides
    @PerActivity
    fun provideBus(): Bus {
        return AsyncBus(com.squareup.otto.ThreadEnforcer.ANY, "default")
    }

    @Provides
    @PerActivity
    fun provideSchedulersManager() : SchedulersManager {
        return SchedulersManager(Schedulers.io(), AndroidSchedulers.mainThread())
    }
}
