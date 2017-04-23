package ru.ls.donkitchen.ui.receiptlist

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import ru.ls.donkitchen.activity.base.SchedulersFactory
import ru.ls.donkitchen.ui.categorylist.ReceiptViewItemConverter
import ru.ls.donkitchen.domain.receipt.ReceiptInteractor
import javax.inject.Inject

@InjectViewState
class ReceiptListPresenter(
        private val categoryId: Int,
        private val categoryName: String,
        component: ReceiptListSubComponent) : MvpPresenter<ReceiptListView>() {
    @Inject lateinit var interactor: ReceiptInteractor
    @Inject lateinit var schedulers: SchedulersFactory
    @Inject lateinit var viewItemConverter: ReceiptViewItemConverter

    init {
        component.inject(this)
    }

    override fun onFirstViewAttach() {
        viewState.setToolbarTitle(categoryName)
        viewState.showLoading()

        interactor.getReceipts(categoryId)
                .observeOn(schedulers.ui())
                .subscribeOn(schedulers.io())
                .subscribeBy(
                        onSuccess = {
                            viewState.hideLoading()
                            if (it.isEmpty()) {
                                viewState.displayNoData()
                            } else {
                                // Пересортировываем список рецептов: рейтинг по-убыванию, название по-возрастанию
                                val sortedResult = it.sortedWith(compareBy({ it.rating }, { it.viewsCount })).reversed()
                                viewState.displayReceipts(sortedResult.map(viewItemConverter::convert))
                            }
                        },
                        onError = {
                            viewState.hideLoading()
                            viewState.displayError(it.localizedMessage)
                        }
                )
    }

    fun upClicks(observable: Observable<Unit>) {
        observable
                .subscribeBy(
                        onNext = {
                            viewState.leaveScreen()
                        },
                        onError = {

                        }
                )
    }
}