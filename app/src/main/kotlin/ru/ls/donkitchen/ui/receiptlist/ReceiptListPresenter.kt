package ru.ls.donkitchen.ui.receiptlist

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import io.reactivex.Observable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import moxy.InjectViewState
import ru.ls.donkitchen.activity.base.SchedulersFactory
import ru.ls.donkitchen.analytics.ANALYTICS_ACTION_CATEGORY_OPENED
import ru.ls.donkitchen.domain.receipt.ReceiptInteractor
import ru.ls.donkitchen.mvp.BasePresenter
import ru.ls.donkitchen.ui.Screens
import ru.ls.donkitchen.ui.categorylist.ReceiptViewItemConverter
import ru.ls.donkitchen.ui.receiptdetail.ReceiptDetail
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ReceiptListPresenter(
        private val categoryId: Int,
        private val categoryName: String,
        component: ReceiptListSubComponent) : BasePresenter<ReceiptListView>() {
    @Inject lateinit var interactor: ReceiptInteractor
    @Inject lateinit var schedulers: SchedulersFactory
    @Inject lateinit var viewItemConverter: ReceiptViewItemConverter
    @Inject lateinit var analytics: FirebaseAnalytics
    @Inject lateinit var router: Router

    init {
        component.inject(this)
    }

    override fun onFirstViewAttach() {
        analytics.logEvent(ANALYTICS_ACTION_CATEGORY_OPENED, Bundle(2).apply {
            putString(FirebaseAnalytics.Param.ITEM_ID, "$categoryId")
            putString(FirebaseAnalytics.Param.ITEM_NAME, categoryName)
        })
        viewState.setToolbarTitle(categoryName)
        viewState.showLoading()

        disposables += interactor.getReceipts(categoryId)
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
        observable.subscribeBy(onNext = { viewState.leaveScreen() })
    }

    fun onReceiptClick(receiptId: Int, receiptName: String) {
        router.navigateTo(Screens.RECEIPT_DETAIL, Bundle(2).apply {
            putInt(ReceiptDetail.EXT_IN_RECEIPT_ID, receiptId)
            putString(ReceiptDetail.EXT_IN_RECEIPT_NAME, receiptName)
        })
    }
}