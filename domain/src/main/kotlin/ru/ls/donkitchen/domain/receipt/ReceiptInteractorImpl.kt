package ru.ls.donkitchen.domain.receipt

import io.reactivex.Single
import ru.ls.donkitchen.core.converter.ReceiptConverter
import ru.ls.donkitchen.core.data.repository.ReceiptRepository
import ru.ls.donkitchen.core.domain.model.ReceiptModel

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 16.04.17
 */
class ReceiptInteractorImpl(private var repository: ReceiptRepository,
                            private var converter: ReceiptConverter): ReceiptInteractor {
    override fun getReceipts(categoryId: Int): Single<List<ReceiptModel>> {
        return repository.getReceipts(categoryId).map { it.map(converter::convert) }
    }
}