package ru.ls.donkitchen.fcm

import android.app.IntentService
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.preference.PreferenceManager
import com.google.firebase.iid.FirebaseInstanceId
import ru.ls.donkitchen.BuildConfig
import ru.ls.donkitchen.R
import ru.ls.donkitchen.activity.base.ServiceSchedulersManager
import ru.ls.donkitchen.app.DonKitchenApplication
import ru.ls.donkitchen.data.rest.Api
import ru.ls.donkitchen.service.base.ServiceModule
import timber.log.Timber
import javax.inject.Inject

/**
 * Регистратор устройства в FCM
 * Получает новый токен и отправляет его на сервер через АПИ для обновления
 *
 * @author Lord (Kuleshov M.V.)
 * @since 24.10.16
 */
class FcmRegistrationIntentService : IntentService("") {
    @Inject lateinit var api: Api
    @Inject lateinit var schedulersManager: ServiceSchedulersManager
    lateinit var prefs: SharedPreferences
//    @Inject
//    fun setSharedPreferences(@ConfigPrefs prefs: SharedPreferences) {
//        this.prefs = prefs
//    }

    override fun onCreate() {
        super.onCreate()

        val app = application as DonKitchenApplication
        val component = app.component().plus(ServiceModule(this))
        component.inject(this)

        prefs = PreferenceManager.getDefaultSharedPreferences(app)
    }

    override fun onHandleIntent(intent: Intent?) {
        val token = FirebaseInstanceId.getInstance().token
        Timber.d("Refreshed token: $token")

        val deviceName = Build.VERSION.RELEASE
        val appName = "${getString(R.string.app_name)} ${BuildConfig.VERSION_NAME}"

//        api.registerFcm("$token", deviceName, appName)
//                .compose(schedulersManager.applySchedulers<FcmRegisterResult>())
//                .subscribeWith {
//                    onNext {
//                        Timber.i("Токен зарегистрирован")
//                    }
//
//                    onError {
//                        Timber.e(it, "Ошибка при регистрации токена")
//                    }
//                }
    }
}
