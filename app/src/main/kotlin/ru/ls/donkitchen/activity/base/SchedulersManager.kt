package ru.ls.donkitchen.activity.base

import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import ru.ls.donkitchen.annotation.IOSched
import ru.ls.donkitchen.annotation.UISched
import rx.Observable
import rx.Scheduler
import javax.inject.Inject

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 21.03.16
 */
class SchedulersManager
@Inject
constructor(private val activity: RxAppCompatActivity,
            @IOSched val io: Scheduler,
            @UISched val ui: Scheduler) {

    fun <T> applySchedulers(): Observable.Transformer<T, T>
            = Observable.Transformer { observable ->
        (observable as Observable)
                .subscribeOn(io)
                .observeOn(ui)
//                .compose((activity as ActivityLifecycleProvider).bindToLifecycle<T>())
    }
}
