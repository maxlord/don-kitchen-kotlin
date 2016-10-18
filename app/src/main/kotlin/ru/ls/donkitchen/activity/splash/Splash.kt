package ru.ls.donkitchen.activity.splash

import android.os.Bundle
import com.crashlytics.android.Crashlytics
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import io.fabric.sdk.android.Fabric
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.android.ContextHolder
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import ru.ls.donkitchen.BuildConfig
import ru.ls.donkitchen.activity.categorylist.CategoryList
import ru.ls.donkitchen.db.DatabaseHelper
import ru.ls.donkitchen.navigate
import timber.log.Timber

/**
 * Сплеш-скрин
 *
 * @author Lord (Kuleshov M.V.)
 * @since 11.01.16
 */
class Splash: RxAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        doAsync {
            // Crashlytics
            if (!BuildConfig.DEBUG) {
                Fabric.with(application, Crashlytics())
            }

            // Инициализируем БД
            try {
                val db = openOrCreateDatabase(DatabaseHelper.DATABASE_NAME, 0, null)
                ContextHolder.setContext(this@Splash)
                val flyway = Flyway()
                flyway.setDataSource("jdbc:sqlite:" + db.path, "", "")
                flyway.isBaselineOnMigrate = true //  setInitOnMigrate(true)
                flyway.migrate()
                db.close()
            } catch (e: Exception) {
                Timber.e(e, "Ошибка инициализации flyway")
            }

            uiThread {
                // Запускаем главный экран
                navigate<CategoryList>(true)
            }
        }
    }

    override fun onBackPressed() {

    }
}
