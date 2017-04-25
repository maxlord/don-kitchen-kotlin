package ru.ls.donkitchen.ui.splash

import android.os.Bundle
import com.arellomobile.mvp.InjectViewState
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import ru.ls.donkitchen.activity.base.SchedulersFactory
import ru.ls.donkitchen.domain.storage.StorageInteractor
import ru.ls.donkitchen.mvp.BasePresenter
import ru.ls.donkitchen.ui.Screens
import ru.ls.donkitchen.ui.receiptdetail.ReceiptDetail
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

/**
 * Презентер для сплеш-экрана
 *
 * @author Lord (Kuleshov M.V.)
 * @since 02.04.17
 */
@InjectViewState
class SplashPresenter(private val args: Bundle?, component: SplashSubComponent) : BasePresenter<SplashView>() {
    @Inject lateinit var schedulers: SchedulersFactory
    @Inject lateinit var interactor: StorageInteractor
    @Inject lateinit var router: Router

    init {
        component.inject(this)
    }

    override fun onFirstViewAttach() {
        disposables += interactor.initialize()
                .observeOn(schedulers.ui())
                .subscribeOn(schedulers.io())
                .subscribeBy(
                        onSuccess = {
                            var displayReceiptId = 0
                            var displayReceiptName = ""
                            if (args != null) {
                                if (args.containsKey(Splash.EXT_IN_DISPLAY_RECEIPT_ID)) {
                                    displayReceiptId = args.getInt(Splash.EXT_IN_DISPLAY_RECEIPT_ID, 0)
                                }
                                if (args.containsKey(Splash.EXT_IN_DISPLAY_RECEIPT_NAME)) {
                                    displayReceiptName = args.getString(Splash.EXT_IN_DISPLAY_RECEIPT_NAME)
                                }
                            }
                            if (displayReceiptId > 0) {
                                val b = Bundle().apply {
                                    putInt(ReceiptDetail.EXT_IN_RECEIPT_ID, displayReceiptId)
                                    putString(ReceiptDetail.EXT_IN_RECEIPT_NAME, displayReceiptName)
                                }
                                router.replaceScreen(Screens.RECEIPT_DETAIL, b)
                            } else {
                                // Запускаем главный экран
                                router.replaceScreen(Screens.CATEGORIES)
                            }
                        },
                        onError = {
                            Timber.e(it, "Ошибка инициализации flyway")
                            router.showSystemMessage("Ошибка инициализации приложения")
                        }
                )
    }
}