package ru.ls.donkitchen.data.repository.category

import ru.ls.donkitchen.core.data.entity.CategoryEntity
import ru.ls.donkitchen.data.rest.response.CategoryListResult

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 09.04.17
 */
interface CategoryEntityConverter {
    fun convert(item: CategoryListResult.CategoryItem): CategoryEntity
}

class CategoryEntityConverterImpl: CategoryEntityConverter {
    override fun convert(item: CategoryListResult.CategoryItem): CategoryEntity {
        return CategoryEntity(
                id = item.id,
                name = item.name,
                imageLink = item.imageLink,
                priority = item.priority,
                receiptCount = item.receiptCount
        )
    }
}
