package ru.ls.donkitchen.domain.category

import ru.ls.donkitchen.core.domain.model.CategoryModel
import rx.Single

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 12.04.17
 */
interface CategoryInteractor {
    fun getCategories(): Single<List<CategoryModel>>
}