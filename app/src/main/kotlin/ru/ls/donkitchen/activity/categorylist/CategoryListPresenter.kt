package ru.ls.donkitchen.activity.categorylist

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.ls.donkitchen.activity.base.SchedulersFactory
import ru.ls.donkitchen.domain.category.CategoryInteractor
import rx.lang.kotlin.subscribeWith
import javax.inject.Inject

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 06.04.17
 */
@InjectViewState
class CategoryListPresenter : MvpPresenter<CategoryListView>() {
    @Inject lateinit var interactor: CategoryInteractor
    @Inject lateinit var schedulers: SchedulersFactory
    @Inject lateinit var viewItemConverter: CategoryViewItemConverter

    fun loadData() {
        viewState.showProgress()

        interactor.getCategories()
                .observeOn(schedulers.ui())
                .subscribeOn(schedulers.io())
                .subscribeWith {
                    onSuccess {
                        viewState.hideProgress()
                        if (it.isEmpty()) {
                            viewState.displayNoData()
                        } else {
                            viewState.displayCategories(it.map(viewItemConverter::convert))
                        }
                    }

                    onError {
                        viewState.hideProgress()
                        viewState.displayError(it.localizedMessage)
                    }
                }
    }
}