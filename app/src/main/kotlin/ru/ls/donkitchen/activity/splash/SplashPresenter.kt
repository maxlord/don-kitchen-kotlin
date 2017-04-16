package ru.ls.donkitchen.activity.splash

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.rxkotlin.subscribeBy
import ru.ls.donkitchen.activity.base.SchedulersFactory
import ru.ls.donkitchen.activity.base.SchedulersFactoryImpl
import ru.ls.donkitchen.domain.storage.StorageInteractor
import timber.log.Timber
import javax.inject.Inject

/**
 * Презентер для сплеш-экрана
 *
 * @author Lord (Kuleshov M.V.)
 * @since 02.04.17
 */
@InjectViewState
class SplashPresenter : MvpPresenter<SplashView>() {
    @Inject lateinit var schedulers: SchedulersFactory
    @Inject lateinit var interactor: StorageInteractor

    fun start() {
        val schedulers = SchedulersFactoryImpl()

        interactor.initialize()
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
