package ru.ls.donkitchen.ui.splash

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import ru.ls.donkitchen.BuildConfig
import ru.ls.donkitchen.activity.base.ActivityModule
import ru.ls.donkitchen.app.DonKitchenApplication
import ru.ls.donkitchen.nav.ActivityNavigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Сплеш-скрин
 *
 * @author Lord (Kuleshov M.V.)
 * @since 11.01.16
 */
class Splash: MvpAppCompatActivity(), SplashView {
    @Inject lateinit var router: Router
    @Inject lateinit var navigatorHolder: NavigatorHolder
    @InjectPresenter lateinit var presenter: SplashPresenter

    @ProvidePresenter
    fun providePresenter(): SplashPresenter {
        return SplashPresenter(intent.extras, DonKitchenApplication.instance().component().plus(SplashModule(this)))
    }

    companion object {
        val EXT_IN_DISPLAY_RECEIPT_ID = "display_receipt_id"
        val EXT_IN_DISPLAY_RECEIPT_NAME = "display_receipt_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DonKitchenApplication.instance().component().plus(ActivityModule(this)).inject(this)

        // Crashlytics
        if (!BuildConfig.DEBUG) {
            Fabric.with(application, Crashlytics())
        }
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(ActivityNavigator(this))
    }

    override fun onBackPressed() {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }
}
