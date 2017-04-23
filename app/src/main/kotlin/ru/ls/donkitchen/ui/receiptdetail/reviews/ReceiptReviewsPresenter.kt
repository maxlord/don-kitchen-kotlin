package ru.ls.donkitchen.ui.receiptdetail.reviews

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.rxkotlin.subscribeBy
import ru.ls.donkitchen.activity.base.SchedulersFactory
import ru.ls.donkitchen.domain.review.ReviewInteractor
import javax.inject.Inject


@InjectViewState
class ReceiptReviewsPresenter(
        private val receiptId: Int,
        component: ReceiptReviewsSubComponent
) : MvpPresenter<ReceiptReviewsView>() {

    @Inject lateinit var interactor: ReviewInteractor
    @Inject lateinit var schedulers: SchedulersFactory
    @Inject lateinit var viewItemConverter: ReviewViewItemConverter

    init {
        component.inject(this)
    }

    override fun onFirstViewAttach() {
        viewState.showLoading()
        interactor.getReviews(receiptId)
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