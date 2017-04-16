package ru.ls.donkitchen.activity.base

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface SchedulersFactory {
    fun io(): Scheduler
    fun ui(): Scheduler
}

class SchedulersFactoryImpl : SchedulersFactory {
    override fun io(): Scheduler {
        return Schedulers.io()
    }

    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}
