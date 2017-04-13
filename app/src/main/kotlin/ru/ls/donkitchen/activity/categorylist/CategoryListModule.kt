package ru.ls.donkitchen.activity.categorylist

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.ls.donkitchen.annotation.PerScreen
import ru.ls.donkitchen.core.data.repository.CategoryRepository
import ru.ls.donkitchen.data.repository.category.CategoryEntityConverter
import ru.ls.donkitchen.data.repository.category.CategoryEntityConverterImpl
import ru.ls.donkitchen.data.repository.category.CategoryRepositoryImpl
import ru.ls.donkitchen.data.rest.Api
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
    fun provideCategoryRepository(context: Context,
                                  api: Api,
                                  converter: CategoryEntityConverter): CategoryRepository {
        return CategoryRepositoryImpl(context, api, converter)
    }

    @PerScreen
    @Provides
    fun provideCategoryEntityConverter(): CategoryEntityConverter {
        return CategoryEntityConverterImpl()
    }
}
