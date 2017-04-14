package ru.ls.donkitchen.activity.categorylist

import dagger.Module
import dagger.Provides
import ru.ls.donkitchen.annotation.PerScreen
import ru.ls.donkitchen.core.data.repository.CategoryRepository
import ru.ls.donkitchen.data.repository.category.CategoryEntityConverter
import ru.ls.donkitchen.data.repository.category.CategoryEntityConverterImpl
import ru.ls.donkitchen.data.repository.category.CategoryRepositoryImpl
import ru.ls.donkitchen.data.rest.Api
import ru.ls.donkitchen.data.storage.ormlite.DatabaseHelper
import ru.ls.donkitchen.domain.category.CategoryInteractor
import ru.ls.donkitchen.domain.category.CategoryInteractorImpl

@Module
class CategoryListModule {
    @PerScreen
    @Provides
    fun provideCategoryInteractor(repository: CategoryRepository): CategoryInteractor {
        return CategoryInteractorImpl(repository)
    }

    @PerScreen
    @Provides
    fun provideCategoryRepository(databaseHelper: DatabaseHelper, api: Api): CategoryRepository {
        return CategoryRepositoryImpl(databaseHelper, api)
    }

    @PerScreen
    @Provides
    fun provideCategoryEntityConverter(): CategoryEntityConverter {
        return CategoryEntityConverterImpl()
    }

    @PerScreen
    @Provides
    fun provideCategoryViewItemConverter(): CategoryViewItemConverter {
        return CategoryViewItemConverterImpl()
    }
}
