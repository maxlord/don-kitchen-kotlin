package ru.ls.donkitchen.data.repository.receipt

import ru.ls.donkitchen.core.data.entity.ReceiptEntity
import ru.ls.donkitchen.data.rest.response.ReceiptListResult

interface ReceiptEntityConverter {
    fun convert(item: ReceiptListResult.ReceiptItem): ReceiptEntity
}

class ReceiptEntityConverterImpl: ReceiptEntityConverter {
    override fun convert(item: ReceiptListResult.ReceiptItem): ReceiptEntity {
        return ReceiptEntity(
                id = item.id,
                categoryId = item.categoryId,
                name = item.name,
                imageLink = item.imageLink,
                ingredients = item.ingredients,
                receipt = item.receipt,
                rating = item.rating,
                viewsCount = item.views
        )
    }
}
