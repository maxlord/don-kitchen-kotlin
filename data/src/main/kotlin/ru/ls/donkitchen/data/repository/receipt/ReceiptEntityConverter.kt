package ru.ls.donkitchen.data.repository.receipt

import ru.ls.donkitchen.core.data.entity.ReceiptEntity
import ru.ls.donkitchen.data.db.table.Receipt
import ru.ls.donkitchen.data.rest.response.ReceiptListResult

interface ReceiptEntityConverter {
    fun convert(item: ReceiptListResult.ReceiptItem): ReceiptEntity
    fun convert(item: Receipt): ReceiptEntity
    fun convertToReceiptItem(item: Receipt): ReceiptListResult.ReceiptItem
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

    override fun convert(item: Receipt): ReceiptEntity {
        return ReceiptEntity(
                id = item.id,
                categoryId = item.category?.id ?: 0,
                name = item.name,
                imageLink = item.imageLink,
                ingredients = item.ingredients,
                receipt = item.receipt,
                rating = item.rating,
                viewsCount = item.viewsCount
        )
    }

    override fun convertToReceiptItem(item: Receipt): ReceiptListResult.ReceiptItem {
        return ReceiptListResult.ReceiptItem().apply {
            id = item.id
            name = item.name
            ingredients = item.ingredients
            receipt = item.receipt
            views = item.viewsCount
            categoryId = item.category?.id ?: 0
            categoryName = item.category?.name ?: ""
            imageLink = item.imageLink
            rating = item.rating
        }
    }

}