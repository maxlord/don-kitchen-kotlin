package ru.ls.donkitchen.activity.receiptdetail.info

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import ru.ls.donkitchen.activity.base.SchedulersFactory
import ru.ls.donkitchen.activity.categorylist.ReceiptViewItemConverter
import ru.ls.donkitchen.activity.receiptdetail.ReceiptDetailSubComponent
import ru.ls.donkitchen.activity.receiptlist.ReceiptViewItem
import ru.ls.donkitchen.domain.receipt.ReceiptInteractor
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class ReceiptDetailInfoPresenter(private val receiptId: Int,
                                 component: ReceiptDetailSubComponent) : MvpPresenter<ReceiptDetailInfoView>() {
    @Inject lateinit var interactor: ReceiptInteractor
    @Inject lateinit var schedulers: SchedulersFactory
    @Inject lateinit var viewItemConverter: ReceiptViewItemConverter

    init {
        component.inject(this)
    }

    override fun onFirstViewAttach() {
        viewState.showLoading()
        interactor.getReceiptDetail(receiptId)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribeBy(
                        onSuccess = {
                            viewState.hideLoading()
                            renderReceipt(viewItemConverter.convert(it))
                        },
                        onError = {
                            Timber.e(it, "Ошибка при получении информации о рецепте")
                            viewState.displayErrorLoadingDialog()
                        }
                )
    }

    private fun renderReceipt(item: ReceiptViewItem) {
        viewState.showTitle(item.name)
        viewState.showImage(item.imageLink ?: "")
        viewState.showViewsCount(item.viewsCount)
        viewState.showRating(item.rating)
        viewState.bindIngredients(item.ingredients)
        viewState.showReceipt(item.receipt)
    }

    fun rateClicks(observable: Observable<Unit>) {
        observable.observeOn(schedulers.ui())
                .subscribeBy(
                        onNext = {
                            // TODO вызвать диалог добавления отзыва
                        }
                )
    }

    fun errorLoadingDialogClicks(single: Single<Unit>) {
        single.observeOn(schedulers.ui())
                .subscribeBy(onSuccess = {
                    viewState.leaveScreen()
                })
    }

}