package ru.ls.donkitchen.ui.splash

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface SplashView : MvpView {

	@StateStrategyType(AddToEndSingleStrategy::class)
	fun showLoading()

	@StateStrategyType(AddToEndSingleStrategy::class)
	fun hideLoading()
}