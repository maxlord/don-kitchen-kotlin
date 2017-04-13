package ru.ls.donkitchen.core.mapper

import ru.ls.donkitchen.core.data.entity.CategoryEntity
import ru.ls.donkitchen.core.domain.model.CategoryModel

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 02.03.17
 */
class CategoryMapper {
    fun mapToModel(entity: CategoryEntity): CategoryModel {
        return CategoryModel(
                entity.id, entity.name, entity.imageLink, entity.receiptCount, entity.priority)
    }

    fun mapToModel(entities: List<CategoryEntity>): List<CategoryModel> {
        val list = arrayListOf<CategoryModel>()

        entities.forEach {
            list.add(mapToModel(it))
        }

        return list
    }
}
