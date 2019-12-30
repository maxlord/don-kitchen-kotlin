package ru.ls.donkitchen.ui.categorylist

import android.os.Bundle
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import moxy.InjectViewState
import ru.ls.donkitchen.activity.base.SchedulersFactory
import ru.ls.donkitchen.domain.category.CategoryInteractor
import ru.ls.donkitchen.mvp.BasePresenter
import ru.ls.donkitchen.ui.Screens
import ru.ls.donkitchen.ui.receiptlist.ReceiptList
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class CategoryListPresenter(component: CategoryListSubComponent) : BasePresenter<CategoryListView>() {
    @Inject lateinit var interactor: CategoryInteractor
    @Inject lateinit var schedulers: SchedulersFactory
    @Inject lateinit var viewItemConverter: CategoryViewItemConverter
    @Inject lateinit var router: Router

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

    fun onCategoryClick(categoryId: Int, categoryName: String) {
        router.navigateTo(Screens.RECEIPTS, Bundle(2).apply {
            putInt(ReceiptList.EXT_IN_CATEGORY_ID, categoryId)
            putString(ReceiptList.EXT_IN_CATEGORY_NAME, categoryName)
        })
    }
}