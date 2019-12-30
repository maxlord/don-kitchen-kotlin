package ru.ls.donkitchen.ui.categorylist

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface CategoryListView: MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showLoading()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideLoading()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun displayNoData()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun displayError(error: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun displayCategories(categories: List<CategoryViewItem>)
}