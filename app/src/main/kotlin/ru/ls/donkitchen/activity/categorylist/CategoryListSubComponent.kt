package ru.ls.donkitchen.activity.categorylist

import dagger.Subcomponent
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
