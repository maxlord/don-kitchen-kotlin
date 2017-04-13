package ru.ls.donkitchen.activity.categorylist

import com.arellomobile.mvp.MvpView
import ru.ls.donkitchen.data.rest.response.CategoryListResult

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 06.04.17
 */
interface CategoryListView: MvpView {
    fun showProgress()

    fun hideProgress()

    fun displayNoData()

    fun displayCategories(categories: List<CategoryViewItem>)
}
