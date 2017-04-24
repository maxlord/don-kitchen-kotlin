package ru.ls.donkitchen.ui.receiptdetail.review

import com.arellomobile.mvp.InjectViewState
import com.jakewharton.rxbinding2.InitialValueObservable
import com.jakewharton.rxbinding2.widget.RatingBarChangeEvent
import io.reactivex.Observable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import ru.ls.donkitchen.R
import ru.ls.donkitchen.activity.base.SchedulersFactory
import ru.ls.donkitchen.domain.review.ReviewInteractor
import ru.ls.donkitchen.mvp.BasePresenter
import ru.ls.donkitchen.ui.receiptdetail.ReceiptDetailSubComponent
import ru.ls.donkitchen.ui.receiptdetail.RxBus
import javax.inject.Inject

@InjectViewState
class ReviewPresenter(private val receiptId: Int, component: ReceiptDetailSubComponent) : BasePresenter<ReviewView>() {
    @Inject lateinit var interactor: ReviewInteractor
    @Inject lateinit var schedulers: SchedulersFactory
    @Inject lateinit var bus: RxBus

    private var ratingValue: Float = 5F
    private var userName: String = ""
    private var comments: String = ""

    init {
        component.inject(this)
    }

    override fun onFirstViewAttach() {
        viewState.showTitle(R.string.activity_receipt_detail_dialog_add_review_title)
        viewState.showRating(ratingValue)
    }

    fun ratingChanges(observable: InitialValueObservable<RatingBarChangeEvent>) {
        disposables += observable.observeOn(schedulers.ui())
                .subscribeBy(onNext = {
                    ratingValue = it.rating()
                })
    }

    fun userNameChanges(observable: InitialValueObservable<CharSequence>) {
        disposables += observable.observeOn(schedulers.ui())
                .subscribeBy(onNext = {
                    userName = it.toString()
                })
    }

    fun commentChanges(observable: InitialValueObservable<CharSequence>) {
        disposables += observable.observeOn(schedulers.ui())
                .subscribeBy(onNext = {
                    comments = it.toString()
                })
    }

    fun positiveClicks(clicks: Observable<Unit>) {
        disposables += clicks.observeOn(schedulers.ui())
                .subscribeBy(onNext = {
                    disposables += interactor.addReview(receiptId, ratingValue.toInt(), userName, comments)
                            .subscribeOn(schedulers.io())
                            .observeOn(schedulers.ui())
                            .subscribeBy(
                                    onSuccess = {
                                        viewState.displayReviewSuccessMessage()
                                        viewState.dismissDialog()
                                        bus.postSaveEvent()
                                    },
                                    onError = {

                                    }
                            )
                })
    }

    fun negativeClicks(clicks: Observable<Unit>) {
        disposables += clicks.observeOn(schedulers.ui())
                .subscribeBy(onNext = {
                    viewState.dismissDialog()
                })
    }
}