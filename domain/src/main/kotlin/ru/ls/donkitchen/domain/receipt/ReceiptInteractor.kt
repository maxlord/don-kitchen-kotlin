package ru.ls.donkitchen.domain.receipt

import io.reactivex.Single
import ru.ls.donkitchen.core.domain.model.ReceiptModel

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 16.04.17
 */
interface ReceiptInteractor {
    fun getReceipts(categoryId: Int): Single<List<ReceiptModel>>
    fun getReceiptDetail(receiptId: Int): Single<ReceiptModel>
    fun incrementReceiptViews(receiptId: Int): Single<Unit>
}