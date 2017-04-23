package ru.ls.donkitchen.domain.receipt

import io.reactivex.Single
import ru.ls.donkitchen.core.converter.ReceiptConverter
import ru.ls.donkitchen.core.data.repository.ReceiptRepository
import ru.ls.donkitchen.core.domain.model.ReceiptModel

class ReceiptInteractorImpl(private var repository: ReceiptRepository,
                            private var converter: ReceiptConverter): ReceiptInteractor {

    override fun getReceipts(categoryId: Int): Single<List<ReceiptModel>> {
        return repository.getReceipts(categoryId).map { it.map(converter::convert) }
    }

    override fun getReceiptDetail(receiptId: Int): Single<ReceiptModel> {
        return repository.getReceiptDetail(receiptId).map(converter::convert)
    }

    override fun incrementReceiptViews(receiptId: Int): Single<Unit> {
        return repository.incrementReceiptViews(receiptId)
    }

}