package ru.ls.donkitchen.activity.splash

import android.os.SystemClock
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.android.ContextHolder
import ru.ls.donkitchen.BuildConfig
import ru.ls.donkitchen.R
import ru.ls.donkitchen.activity.base.SchedulersManager
import ru.ls.donkitchen.activity.categorylist.CategoryList
import ru.ls.donkitchen.app.DonKitchenApplication
import ru.ls.donkitchen.db.DatabaseHelper
import ru.ls.donkitchen.fragment.base.BaseFragment
import ru.ls.donkitchen.helper.ActivityHelper
import ru.ls.donkitchen.rest.Api
import timber.log.Timber
import javax.inject.Inject

/**
 * Стартовый экран
 *
 * @author Lord (Kuleshov M.V.)
 * @since 16.03.16
 */
class SplashFragment: BaseFragment() {
    companion object {
        fun newInstance(): SplashFragment {
            val f = SplashFragment()
            return f
        }
    }

    @Inject lateinit var application: DonKitchenApplication
    @Inject lateinit var api: Api
    @Inject lateinit var schedulersManager: SchedulersManager

    override fun inject() {
        getComponent().inject(this)
    }

    override fun onStart() {
        super.onStart()

        IntentLauncher().start()
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_splash
    }

    private inner class IntentLauncher : Thread() {
        /**
         * Sleep for some time and than start new activity.
         */
        override fun run() {
            // Crashlytics
            if (!BuildConfig.DEBUG) {
                Fabric.with(application, Crashlytics())
            }

            // Инициализируем БД
            try {
                val db = activity.openOrCreateDatabase(DatabaseHelper.DATABASE_NAME, 0, null)
                ContextHolder.setContext(activity)
                val flyway = Flyway()
                flyway.setDataSource("jdbc:sqlite:" + db.path, "", "")
                flyway.isBaselineOnMigrate = true //  setInitOnMigrate(true)
                flyway.migrate()
                db.close()
            } catch (e: Exception) {
                Timber.e(e, "Ошибка инициализации flyway")
            }

            SystemClock.sleep(1000)

            startMainActivity()
        }
    }

    private fun startMainActivity() {
        // Запускаем главный экран
        ActivityHelper.startActivity(activity, CategoryList::class.java, true)
    }
}
