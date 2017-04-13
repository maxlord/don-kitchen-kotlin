package ru.ls.donkitchen.activity.categorylist

import ru.ls.donkitchen.core.data.entity.CategoryEntity

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 10.04.17
 */
interface CategoryViewItemConverter {
    fun convert(item: CategoryEntity): CategoryViewItem
}

class CategoryViewItemConverterImpl: CategoryViewItemConverter {
    override fun convert(item: CategoryEntity): CategoryViewItem {
        return CategoryViewItem(
                id = item.id,
                name = item.name,
                imageLink = item.imageLink,
                priority =  item.priority,
                receiptCount = item.receiptCount
        )
    }

}
