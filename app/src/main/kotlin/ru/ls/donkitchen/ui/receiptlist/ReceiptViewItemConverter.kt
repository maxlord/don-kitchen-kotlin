package ru.ls.donkitchen.ui.categorylist

import ru.ls.donkitchen.ui.receiptlist.ReceiptViewItem
import ru.ls.donkitchen.core.domain.model.ReceiptModel

interface ReceiptViewItemConverter {
    fun convert(item: ReceiptModel): ReceiptViewItem
}

class ReceiptViewItemConverterImpl: ReceiptViewItemConverter {
    override fun convert(item: ReceiptModel): ReceiptViewItem {
        return ReceiptViewItem(
                id = item.id,
                name = item.name,
                imageLink = item.imageLink,
                categoryId = item.categoryId,
                ingredients = item.ingredients,
                receipt = item.receipt,
                rating = item.rating,
                viewsCount = item.viewsCount
        )
    }

}
