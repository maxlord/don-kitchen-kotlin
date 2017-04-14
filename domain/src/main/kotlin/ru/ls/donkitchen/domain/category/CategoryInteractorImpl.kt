package ru.ls.donkitchen.domain.category

import ru.ls.donkitchen.core.data.repository.CategoryRepository
import ru.ls.donkitchen.core.domain.model.CategoryModel
import ru.ls.donkitchen.core.mapper.CategoryMapper
import rx.Single

class CategoryInteractorImpl(private var categoryRepository: CategoryRepository) : CategoryInteractor {
    override fun getCategories(): Single<List<CategoryModel>> {
        return categoryRepository.getCategories().map(CategoryMapper()::mapToModel)
    }
}