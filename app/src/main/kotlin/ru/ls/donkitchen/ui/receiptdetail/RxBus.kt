package ru.ls.donkitchen.ui.receiptdetail

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

class RxBus {
    private val createEvents = PublishSubject.create<Unit>()
    private val saveEvents = PublishSubject.create<Unit>()

    init {
        Timber.d("Создан экземпляр RxBus")
    }

    fun reviewCreateEvents(): Observable<Unit> {
        return createEvents
    }

    fun postCreateEvent() {
        createEvents.onNext(Unit)
    }

    fun reviewSaveEvents(): Observable<Unit> {
        return saveEvents
    }

    fun postSaveEvent() {
        saveEvents.onNext(Unit)
    }
}