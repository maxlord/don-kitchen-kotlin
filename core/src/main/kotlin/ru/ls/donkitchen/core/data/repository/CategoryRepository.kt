package ru.ls.donkitchen.core.data.repository

import io.reactivex.Single
import ru.ls.donkitchen.core.data.entity.CategoryEntity

interface CategoryRepository {

    fun getCategories(): Single<List<CategoryEntity>>

}
