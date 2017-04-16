package ru.ls.donkitchen.core.converter

import ru.ls.donkitchen.core.data.entity.CategoryEntity
import ru.ls.donkitchen.core.domain.model.CategoryModel

interface CategoryConverter {
    fun convert(entity: CategoryEntity): CategoryModel
}

class CategoryConverterImpl: CategoryConverter {
    override fun convert(entity: CategoryEntity): CategoryModel {
        with(entity) {
            return CategoryModel(id, name, imageLink, receiptCount, priority)
        }
    }
}