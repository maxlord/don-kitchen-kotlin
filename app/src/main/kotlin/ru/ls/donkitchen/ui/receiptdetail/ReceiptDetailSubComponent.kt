package ru.ls.donkitchen.ui.receiptdetail

import dagger.Subcomponent
import ru.ls.donkitchen.annotation.PerScreen
import ru.ls.donkitchen.ui.receiptdetail.info.ReceiptDetailInfoPresenter
import ru.ls.donkitchen.ui.receiptdetail.review.ReviewPresenter
import ru.ls.donkitchen.ui.receiptdetail.reviews.ReceiptReviewsPresenter

@PerScreen
@Subcomponent(modules = arrayOf(ReceiptDetailModule::class))
interface ReceiptDetailSubComponent {
    fun inject(presenter: ReceiptDetailPresenter)
    fun inject(presenter: ReceiptDetailInfoPresenter)
    fun inject(presenter: ReceiptReviewsPresenter)
    fun inject(presenter: ReviewPresenter)
}