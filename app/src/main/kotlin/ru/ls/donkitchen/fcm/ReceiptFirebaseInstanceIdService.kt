package ru.ls.donkitchen.fcm

import android.content.Intent
import com.google.firebase.iid.FirebaseInstanceIdService
import ru.ls.donkitchen.fcm.FcmRegistrationIntentService

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 22.10.16
 */
class ReceiptFirebaseInstanceIdService: FirebaseInstanceIdService() {
    override fun onTokenRefresh() {
        super.onTokenRefresh()

        val intent = Intent(this, FcmRegistrationIntentService::class.java)
        startService(intent)
    }
}
