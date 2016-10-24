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
import ru.ls.donkitchen.activity.receiptdetail.ReceiptDetail
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
    companion object {
        val EXT_IN_DISPLAY_RECEIPT_ID = "display_receipt_id"
        val EXT_IN_DISPLAY_RECEIPT_NAME = "display_receipt_name"
    }

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
                var displayReceiptId = 0
                var displayReceiptName = ""
                if (intent != null) {
                    if (intent.hasExtra(EXT_IN_DISPLAY_RECEIPT_ID)) {
                        displayReceiptId = intent.getIntExtra(EXT_IN_DISPLAY_RECEIPT_ID, 0)
                    }
                    if (intent.hasExtra(EXT_IN_DISPLAY_RECEIPT_NAME)) {
                        displayReceiptName = intent.getStringExtra(EXT_IN_DISPLAY_RECEIPT_NAME)
                    }
                }

                if (displayReceiptId > 0) {
                    val b = Bundle()
                    b.putInt(ReceiptDetail.EXT_IN_RECEIPT_ID, displayReceiptId)
                    b.putString(ReceiptDetail.EXT_IN_RECEIPT_NAME, displayReceiptName)

                    navigate<ReceiptDetail>(true, b)
                } else {
                    // Запускаем главный экран
                    navigate<CategoryList>(true)
                }
            }
        }
    }

    override fun onBackPressed() {

    }
}
