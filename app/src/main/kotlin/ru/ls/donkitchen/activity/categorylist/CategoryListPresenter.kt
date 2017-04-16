package ru.ls.donkitchen.activity.categorylist

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.rxkotlin.subscribeBy
import ru.ls.donkitchen.activity.base.SchedulersFactory
import ru.ls.donkitchen.domain.category.CategoryInteractor
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

    fun start() {
        viewState.showProgress()

        interactor.getCategories()
                .observeOn(schedulers.ui())
                .subscribeOn(schedulers.io())
                .subscribeBy(
                        onSuccess = {
                            viewState.hideProgress()
                            if (it.isEmpty()) {
                                viewState.displayNoData()
                            } else {
                                viewState.displayCategories(it.map(viewItemConverter::convert))
                            }
                        },
                        onError = {
                            viewState.hideProgress()
                            viewState.displayError(it.localizedMessage)
                        }
                )
    }
}