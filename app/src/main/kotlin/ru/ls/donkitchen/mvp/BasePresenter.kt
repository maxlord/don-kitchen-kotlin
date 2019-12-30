package ru.ls.donkitchen.mvp

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import moxy.MvpPresenter
import moxy.MvpView

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