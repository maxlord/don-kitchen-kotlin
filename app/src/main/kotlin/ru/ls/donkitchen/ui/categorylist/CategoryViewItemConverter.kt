package ru.ls.donkitchen.ui.categorylist

import ru.ls.donkitchen.core.domain.model.CategoryModel

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 10.04.17
 */
interface CategoryViewItemConverter {
    fun convert(item: CategoryModel): CategoryViewItem
}

class CategoryViewItemConverterImpl: CategoryViewItemConverter {
    override fun convert(item: CategoryModel): CategoryViewItem {
        return CategoryViewItem(
                id = item.id,
                name = item.name,
                imageLink = item.imageLink,
                priority =  item.priority,
                receiptCount = item.receiptCount
        )
    }

}
