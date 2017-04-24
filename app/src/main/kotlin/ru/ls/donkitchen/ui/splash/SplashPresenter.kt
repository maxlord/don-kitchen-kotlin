package ru.ls.donkitchen.ui.splash

import com.arellomobile.mvp.InjectViewState
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import ru.ls.donkitchen.activity.base.SchedulersFactory
import ru.ls.donkitchen.activity.base.SchedulersFactoryImpl
import ru.ls.donkitchen.domain.storage.StorageInteractor
import ru.ls.donkitchen.mvp.BasePresenter
import timber.log.Timber
import javax.inject.Inject

/**
 * Презентер для сплеш-экрана
 *
 * @author Lord (Kuleshov M.V.)
 * @since 02.04.17
 */
@InjectViewState
class SplashPresenter : BasePresenter<SplashView>() {
    @Inject lateinit var schedulers: SchedulersFactory
    @Inject lateinit var interactor: StorageInteractor

    fun start() {
        val schedulers = SchedulersFactoryImpl()

        disposables += interactor.initialize()
                .observeOn(schedulers.ui())
                .subscribeOn(schedulers.io())
                .subscribeBy(
                        onSuccess = {
                            viewState.startActivity()
                        },
                        onError = {
                            Timber.e(it, "Ошибка инициализации flyway")
                        }
                )
    }
}
