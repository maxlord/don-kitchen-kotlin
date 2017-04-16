package ru.ls.donkitchen.core.data.repository

import io.reactivex.Single
import ru.ls.donkitchen.core.data.entity.CategoryEntity

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 02.03.17
 */
interface CategoryRepository {
    fun getCategories(): Single<List<CategoryEntity>>
}
