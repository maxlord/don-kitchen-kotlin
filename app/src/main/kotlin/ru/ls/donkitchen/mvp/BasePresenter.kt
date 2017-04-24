package ru.ls.donkitchen.mvp

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign

/**
 * Базовый презентер с возможностью авто-отписки от RX при уничтожении презентера
 */
open class BasePresenter<V : MvpView> : MvpPresenter<V>() {
    protected val disposables = CompositeDisposable()

    protected fun unsubscribeOnDestroy(disposable: Disposable) {
        disposables += disposable
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}