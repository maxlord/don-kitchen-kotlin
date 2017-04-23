package ru.ls.donkitchen.ui.receiptdetail.reviews

import dagger.Subcomponent
import ru.ls.donkitchen.annotation.PerScreen

@PerScreen
@Subcomponent(modules = arrayOf(ReceiptReviewsModule::class))
interface ReceiptReviewsSubComponent {
    fun inject(presenter: ReceiptReviewsPresenter)
}
