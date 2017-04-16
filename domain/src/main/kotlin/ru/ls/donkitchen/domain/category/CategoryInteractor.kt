package ru.ls.donkitchen.domain.category

import io.reactivex.Single
import ru.ls.donkitchen.core.domain.model.CategoryModel

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 12.04.17
 */
interface CategoryInteractor {
    fun getCategories(): Single<List<CategoryModel>>
}