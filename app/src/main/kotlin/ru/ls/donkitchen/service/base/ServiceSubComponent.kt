package ru.ls.donkitchen.service.base

import dagger.Subcomponent
import ru.ls.donkitchen.annotation.PerService
import ru.ls.donkitchen.fcm.FcmRegistrationIntentService
import ru.ls.donkitchen.fcm.ReceiptFirebaseMessagingService

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 15.04.16
 */
@PerService
@Subcomponent(modules = arrayOf(ServiceModule::class))
interface ServiceSubComponent {
    fun inject(service: FcmRegistrationIntentService)
    fun inject(service: ReceiptFirebaseMessagingService)
}
