package ru.ls.donkitchen.core.data.repository

import ru.ls.donkitchen.core.data.entity.CategoryEntity
import rx.Single

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 02.03.17
 */
interface CategoryRepository {
    fun getCategories(): Single<List<CategoryEntity>>
}
