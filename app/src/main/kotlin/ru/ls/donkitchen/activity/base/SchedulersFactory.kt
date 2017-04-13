package ru.ls.donkitchen.activity.base

import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

interface SchedulersFactory {
    fun io(): Scheduler
    fun ui(): Scheduler
}

class SchedulersFactoryImpl: SchedulersFactory {
    override fun io(): Scheduler {
        return Schedulers.io()
    }

    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}
