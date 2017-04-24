package ru.ls.donkitchen.ui.receiptdetail.reviews

import com.arellomobile.mvp.InjectViewState
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import ru.ls.donkitchen.activity.base.SchedulersFactory
import ru.ls.donkitchen.domain.review.ReviewInteractor
import ru.ls.donkitchen.mvp.BasePresenter
import ru.ls.donkitchen.ui.receiptdetail.ReceiptDetailSubComponent
import ru.ls.donkitchen.ui.receiptdetail.RxBus
import javax.inject.Inject


@InjectViewState
class ReceiptReviewsPresenter(
        private val receiptId: Int,
        component: ReceiptDetailSubComponent
) : BasePresenter<ReceiptReviewsView>() {

    @Inject lateinit var interactor: ReviewInteractor
    @Inject lateinit var schedulers: SchedulersFactory
    @Inject lateinit var viewItemConverter: ReviewViewItemConverter
    @Inject lateinit var bus: RxBus

    init {
        component.inject(this)
    }

    override fun onFirstViewAttach() {
        loadReviews()
        disposables += bus.reviewSaveEvents()
                .observeOn(schedulers.ui())
                .subscribeBy(onNext = {
                    loadReviews()
                })
    }

    private fun loadReviews() {
        viewState.showLoading()
        disposables += interactor.getReviews(receiptId)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribeBy(
                        onSuccess = {
                            viewState.hideLoading()
                            if (it.isEmpty()) {
                                viewState.displayNoData()
                            } else {
                                viewState.displayReviews(it.map(viewItemConverter::convert))
                            }
                        },
                        onError = {
                            viewState.hideLoading()
                            viewState.displayError(it.localizedMessage)
                        }
                )
    }

}