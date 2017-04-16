package ru.ls.donkitchen.core.converter

import ru.ls.donkitchen.core.data.entity.ReceiptEntity
import ru.ls.donkitchen.core.domain.model.ReceiptModel

interface ReceiptConverter {
    fun convert(entity: ReceiptEntity): ReceiptModel
}

class ReceiptConverterImpl : ReceiptConverter {
    override fun convert(entity: ReceiptEntity): ReceiptModel {
        with(entity) {
            return ReceiptModel(id, categoryId, name, imageLink,
                    ingredients, receipt, viewsCount, rating)
        }
    }
}