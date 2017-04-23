package ru.ls.donkitchen.ui.splash

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import ru.ls.donkitchen.BuildConfig
import ru.ls.donkitchen.activity.base.ActivityModule
import ru.ls.donkitchen.ui.categorylist.CategoryList
import ru.ls.donkitchen.ui.receiptdetail.ReceiptDetail
import ru.ls.donkitchen.app.DonKitchenApplication
import ru.ls.donkitchen.navigate

/**
 * Сплеш-скрин
 *
 * @author Lord (Kuleshov M.V.)
 * @since 11.01.16
 */
class Splash: MvpAppCompatActivity(), SplashView {
    @InjectPresenter lateinit var presenter: SplashPresenter

    companion object {
        val EXT_IN_DISPLAY_RECEIPT_ID = "display_receipt_id"
        val EXT_IN_DISPLAY_RECEIPT_NAME = "display_receipt_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val component = (application as DonKitchenApplication)
                .component()
                .plus(ActivityModule(this))
                .plus(SplashModule(this))
        component.inject(presenter)

        // Crashlytics
        if (!BuildConfig.DEBUG) {
            Fabric.with(application, Crashlytics())
        }
        presenter.start()
    }

    override fun onBackPressed() {

    }

    override fun showLoading() {

    }

    override fun startActivity() {
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
            b.apply {
                putInt(ReceiptDetail.EXT_IN_RECEIPT_ID, displayReceiptId)
                putString(ReceiptDetail.EXT_IN_RECEIPT_NAME, displayReceiptName)
            }
            navigate<ReceiptDetail>(true, b)
        } else {
            // Запускаем главный экран
            navigate<CategoryList>(true)
        }
    }
}
