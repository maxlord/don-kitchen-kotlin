package ru.ls.donkitchen.ui.receiptdetail.info

import android.os.Bundle
import com.arellomobile.mvp.InjectViewState
import com.google.firebase.analytics.FirebaseAnalytics
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import ru.ls.donkitchen.activity.base.SchedulersFactory
import ru.ls.donkitchen.analytics.ANALYTICS_ACTION_ADD_RATE_BUTTON
import ru.ls.donkitchen.domain.receipt.ReceiptInteractor
import ru.ls.donkitchen.mvp.BasePresenter
import ru.ls.donkitchen.ui.categorylist.ReceiptViewItemConverter
import ru.ls.donkitchen.ui.receiptdetail.ReceiptDetailSubComponent
import ru.ls.donkitchen.ui.receiptdetail.RxBus
import ru.ls.donkitchen.ui.receiptlist.ReceiptViewItem
import timber.log.Timber
import javax.inject.Inject


@InjectViewState
class ReceiptDetailInfoPresenter(private val receiptId: Int,
                                 component: ReceiptDetailSubComponent) : BasePresenter<ReceiptDetailInfoView>() {
    @Inject lateinit var interactor: ReceiptInteractor
    @Inject lateinit var schedulers: SchedulersFactory
    @Inject lateinit var viewItemConverter: ReceiptViewItemConverter
    @Inject lateinit var bus: RxBus
    @Inject lateinit var analytics: FirebaseAnalytics

    private var receiptName: String = ""

    init {
        component.inject(this)
    }

    override fun onFirstViewAttach() {
        viewState.showLoading()
        disposables += interactor.getReceiptDetail(receiptId)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribeBy(
                        onSuccess = {
                            viewState.hideLoading()
                            receiptName = it.name
                            val receipt = viewItemConverter.convert(it)
                            renderReceipt(receipt)
                            bus.postReceiptEvent(receipt)
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
        disposables += observable.observeOn(schedulers.ui())
                .subscribeBy(
                        onNext = {
                            bus.postCreateEvent()
                            analytics.logEvent(ANALYTICS_ACTION_ADD_RATE_BUTTON, Bundle().apply {
                                putString(FirebaseAnalytics.Param.ITEM_ID, "$receiptId")
                                putString(FirebaseAnalytics.Param.ITEM_NAME, receiptName)
                            })
                        }
                )
    }

    fun errorLoadingDialogClicks(single: Single<Unit>) {
        disposables += single.observeOn(schedulers.ui())
                .subscribeBy(onSuccess = {
                    viewState.leaveScreen()
                })
    }

}