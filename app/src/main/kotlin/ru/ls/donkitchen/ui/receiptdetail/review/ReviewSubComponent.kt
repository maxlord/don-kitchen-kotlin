package ru.ls.donkitchen.ui.receiptdetail.review

import dagger.Subcomponent
import ru.ls.donkitchen.annotation.PerScreen
import ru.ls.donkitchen.ui.receiptdetail.reviews.ReviewModule

@PerScreen
@Subcomponent(modules = arrayOf(ReviewModule::class))
interface ReviewSubComponent {
    fun inject(presenter: ReviewPresenter)
}
