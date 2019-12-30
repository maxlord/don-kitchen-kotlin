package ru.ls.donkitchen.fcm

import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import timber.log.Timber

class ReceiptFirebaseInstanceIdService : FirebaseMessagingService() {

	override fun onNewToken(token: String) {
		super.onNewToken(token)
		try {
			val intent = Intent(this, FcmRegistrationIntentService::class.java)
			startService(intent)
		} catch (e: Exception) {
			Timber.e(e)
		}
	}
}