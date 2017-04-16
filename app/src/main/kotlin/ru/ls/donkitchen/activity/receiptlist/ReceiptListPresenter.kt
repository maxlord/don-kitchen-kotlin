package ru.ls.donkitchen.activity.receiptlist

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.rxkotlin.subscribeBy
import ru.ls.donkitchen.activity.base.SchedulersFactory
import ru.ls.donkitchen.activity.categorylist.ReceiptViewItemConverter
import ru.ls.donkitchen.domain.receipt.ReceiptInteractor
import javax.inject.Inject

@InjectViewState
class ReceiptListPresenter: MvpPresenter<ReceiptListView>() {
    @Inject lateinit var interactor: ReceiptInteractor
    @Inject lateinit var schedulers: SchedulersFactory
    @Inject lateinit var viewItemConverter: ReceiptViewItemConverter

    fun start(categoryId: Int, categoryName: String) {
        viewState.setToolbarTitle(categoryName)
        viewState.showProgress()

        interactor.getReceipts(categoryId)
                .observeOn(schedulers.ui())
                .subscribeOn(schedulers.io())
                .subscribeBy(
                        onSuccess = {
                            viewState.hideProgress()
                            if (it.isEmpty()) {
                                viewState.displayNoData()
                            } else {
                                // Пересортировываем список рецептов: рейтинг по-убыванию, название по-возрастанию
                                val sortedResult = it.sortedWith(compareBy({ it.rating }, { it.viewsCount })).reversed()
                                viewState.displayReceipts(sortedResult.map(viewItemConverter::convert))
                            }
                        },
                        onError = {
                            viewState.hideProgress()
                            viewState.displayError(it.localizedMessage)
                        }
                )
    }

    fun onBackAction() {
        viewState.leaveScreen()
    }
}