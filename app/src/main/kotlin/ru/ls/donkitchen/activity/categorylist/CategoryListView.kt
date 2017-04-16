package ru.ls.donkitchen.activity.categorylist

import com.arellomobile.mvp.MvpView

interface CategoryListView: MvpView {
    fun showProgress()

    fun hideProgress()

    fun displayNoData()

    fun displayError(error: String)

    fun displayCategories(categories: List<CategoryViewItem>)
}
