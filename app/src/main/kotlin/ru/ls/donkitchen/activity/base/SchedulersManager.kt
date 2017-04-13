package ru.ls.donkitchen.activity.base

import ru.ls.donkitchen.annotation.IOSched
import ru.ls.donkitchen.annotation.UISched
import rx.Observable
import rx.Scheduler
import rx.Single
import javax.inject.Inject

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 21.03.16
 */
@Deprecated("Use SchedulersFactory")
class SchedulersManager
@Inject
constructor(@IOSched val io: Scheduler, @UISched val ui: Scheduler) {

    fun <T> applySchedulers(): Observable.Transformer<T, T>
            = Observable.Transformer { observable ->
        (observable as Observable)
                .subscribeOn(io)
                .observeOn(ui)
    }

    fun <T> applySingleSchedulers(): Single.Transformer<T, T>
            = Single.Transformer { observable ->
        (observable as Single)
                .subscribeOn(io)
                .observeOn(ui)
    }
}
