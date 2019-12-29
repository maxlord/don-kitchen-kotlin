package ru.ls.donkitchen.ui.receiptdetail

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import ru.ls.donkitchen.ui.receiptlist.ReceiptViewItem
import timber.log.Timber

class RxBus {
    private val createEvents = PublishSubject.create<Unit>()
    private val saveEvents = PublishSubject.create<Unit>()
    private val receiptEvents = PublishSubject.create<ReceiptViewItem>()

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

    fun receiptEvents(): Observable<ReceiptViewItem> {
        return receiptEvents
    }

    fun postReceiptEvent(receipt: ReceiptViewItem) {
        receiptEvents.onNext(receipt)
    }

}