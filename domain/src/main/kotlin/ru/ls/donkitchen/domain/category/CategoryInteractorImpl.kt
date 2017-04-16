package ru.ls.donkitchen.domain.category

import io.reactivex.Single
import ru.ls.donkitchen.core.converter.CategoryConverter
import ru.ls.donkitchen.core.data.repository.CategoryRepository
import ru.ls.donkitchen.core.domain.model.CategoryModel

class CategoryInteractorImpl(private var categoryRepository: CategoryRepository,
                             private var converter: CategoryConverter) : CategoryInteractor {
    override fun getCategories(): Single<List<CategoryModel>> {
        return categoryRepository.getCategories().map { it.map(converter::convert) }
    }
}