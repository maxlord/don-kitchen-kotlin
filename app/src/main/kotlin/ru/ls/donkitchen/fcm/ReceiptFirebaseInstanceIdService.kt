package ru.ls.donkitchen.fcm

import android.content.Intent
import com.google.firebase.iid.FirebaseInstanceIdService
import timber.log.Timber
import java.lang.Exception

class ReceiptFirebaseInstanceIdService: FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        super.onTokenRefresh()
        try {
            val intent = Intent(this, FcmRegistrationIntentService::class.java)
            startService(intent)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

}