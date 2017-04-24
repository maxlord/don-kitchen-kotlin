package ru.ls.donkitchen.ui.categorylist

import com.arellomobile.mvp.InjectViewState
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import ru.ls.donkitchen.activity.base.SchedulersFactory
import ru.ls.donkitchen.domain.category.CategoryInteractor
import ru.ls.donkitchen.mvp.BasePresenter
import javax.inject.Inject

@InjectViewState
class CategoryListPresenter(component: CategoryListSubComponent) : BasePresenter<CategoryListView>() {
    @Inject lateinit var interactor: CategoryInteractor
    @Inject lateinit var schedulers: SchedulersFactory
    @Inject lateinit var viewItemConverter: CategoryViewItemConverter

    init {
        component.inject(this)
    }

    override fun onFirstViewAttach() {
        viewState.showLoading()

        disposables += interactor.getCategories()
                .observeOn(schedulers.ui())
                .subscribeOn(schedulers.io())
                .subscribeBy(
                        onSuccess = {
                            viewState.hideLoading()
                            if (it.isEmpty()) {
                                viewState.displayNoData()
                            } else {
                                viewState.displayCategories(it.map(viewItemConverter::convert))
                            }
                        },
                        onError = {
                            viewState.hideLoading()
                            viewState.displayError(it.localizedMessage)
                        }
                )
    }
}