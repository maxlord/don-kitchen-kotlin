package ru.ls.donkitchen.ui.categorylist

import com.arellomobile.mvp.MvpView

interface CategoryListView: MvpView {
    fun showLoading()

    fun hideLoading()

    fun displayNoData()

    fun displayError(error: String)

    fun displayCategories(categories: List<CategoryViewItem>)
}
