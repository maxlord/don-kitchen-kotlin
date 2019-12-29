package ru.ls.donkitchen.fcm

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.dao.Dao
import io.reactivex.rxkotlin.subscribeBy
import ru.ls.donkitchen.R
import ru.ls.donkitchen.activity.base.SchedulersFactory
import ru.ls.donkitchen.app.DonKitchenApplication
import ru.ls.donkitchen.data.db.table.Category
import ru.ls.donkitchen.data.db.table.Receipt
import ru.ls.donkitchen.data.rest.Api
import ru.ls.donkitchen.data.rest.response.ReceiptDetailResult
import ru.ls.donkitchen.data.storage.ormlite.DatabaseHelper
import ru.ls.donkitchen.service.base.ServiceModule
import ru.ls.donkitchen.ui.splash.Splash
import timber.log.Timber
import java.sql.SQLException
import javax.inject.Inject

/**
 * Сервис получения push-уведомлений от FCM
 *
 * @author Lord (Kuleshov M.V.)
 * @since 22.10.16
 */
class ReceiptFirebaseMessagingService : FirebaseMessagingService() {
    companion object {
        val NOTIFICATION_ID_RECEIPT = 10001

        val REQUEST_INTENT_NOTIFICATION = 10002
    }

    @Inject lateinit var api: Api
    @Inject lateinit var schedulers: SchedulersFactory
    @Inject lateinit var databaseHelper: DatabaseHelper

    override fun onCreate() {
        super.onCreate()

        val app = application as DonKitchenApplication
        val component = app.component().plus(ServiceModule(this))
        component.inject(this)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)

        Timber.d("From: ${remoteMessage?.from}")

        // Check if message contains a data payload.
        if (remoteMessage?.data != null) {
            if (remoteMessage.data.isNotEmpty()) {
                Timber.d("Message data payload: ${remoteMessage.data}")

                val receiptId = remoteMessage.data["rid"]?.toInt()

                if (receiptId != null) {
                    api.getReceiptDetail(receiptId)
                            .subscribeOn(schedulers.io())
                            .observeOn(schedulers.ui())
                            .subscribeBy(
                                    onSuccess = {
                                        if (it != null) {
                                            // Сохраняем в БД
                                            try {
                                                val categoryDao: Dao<Category, Int> = databaseHelper.getDao(Category::class.java)
                                                val receiptDao: Dao<Receipt, Int> = databaseHelper.getDao(Receipt::class.java)

                                                // Сохраняем категорию, если она новая
                                                var cat = categoryDao.queryForId(it.categoryId)
                                                if (cat == null) {
                                                    cat = Category()
                                                    cat.id = it.categoryId
                                                    cat.name = it.categoryName
                                                    cat.imageLink = ""
                                                    cat.receiptCount = 1
                                                    cat.priority = 0

                                                    categoryDao.createOrUpdate(cat)
                                                }

                                                // Сохраняем рецепт
                                                var r: Receipt? = receiptDao.queryForId(it.id)
                                                if (r == null) {
                                                    r = Receipt()
                                                    r.id = it.id
                                                    r.category = cat
                                                    r.name = it.name
                                                    r.imageLink = it.imageLink
                                                    r.ingredients = it.ingredients
                                                    r.receipt = it.receipt
                                                    r.viewsCount = it.views
                                                    r.rating = it.rating

                                                    receiptDao.createOrUpdate(r)
                                                }
                                            } catch (e: SQLException) {
                                                Timber.e(e, "[Уведомления] Ошибка сохранения рецепта в БД")
                                            } finally {
                                                OpenHelperManager.releaseHelper()
                                            }

//                                            generateAlarmNotification(it)
                                        }
                                    },
                                    onError = {
                                        Timber.e(it, "Ошибка при получении рецепта из push-уведомления")
                                    }
                            )
                }
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage?.notification != null) {
            Timber.d("Message Notification Body: ${remoteMessage.notification.body}")
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private fun generateAlarmNotification(receipt: ReceiptDetailResult) {
        val notificationIntent = Intent(application, Splash::class.java)
        val args = Bundle()
        args.putInt(Splash.EXT_IN_DISPLAY_RECEIPT_ID, receipt.id)
        args.putString(Splash.EXT_IN_DISPLAY_RECEIPT_NAME, receipt.name)
        notificationIntent.putExtras(args)

        notificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val intent = PendingIntent.getActivity(application, REQUEST_INTENT_NOTIFICATION, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(application, "channelId")
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Новый рецепт '${receipt.name}'")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentIntent(intent)
                .build()

        notification.defaults = notification.defaults or Notification.DEFAULT_SOUND
        notification.defaults = notification.defaults or Notification.DEFAULT_VIBRATE

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID_RECEIPT, notification)
    }
}
