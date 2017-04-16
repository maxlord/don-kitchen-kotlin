package ru.ls.donkitchen.core.data.repository

import io.reactivex.Single
import ru.ls.donkitchen.core.data.entity.ReceiptEntity

interface ReceiptRepository {
    fun getReceipts(categoryId: Int): Single<List<ReceiptEntity>>
}