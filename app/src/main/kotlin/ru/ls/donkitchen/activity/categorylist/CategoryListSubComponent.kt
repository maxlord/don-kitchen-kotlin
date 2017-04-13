package ru.ls.donkitchen.activity.splash

import dagger.Subcomponent
import ru.ls.donkitchen.activity.categorylist.CategoryListModule
import ru.ls.donkitchen.activity.categorylist.CategoryListPresenter
import ru.ls.donkitchen.annotation.PerScreen

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 02.04.17
 */
@PerScreen
@Subcomponent(modules = arrayOf(CategoryListModule::class))
interface CategoryListSubComponent {
    fun inject(presenter: CategoryListPresenter)
}
