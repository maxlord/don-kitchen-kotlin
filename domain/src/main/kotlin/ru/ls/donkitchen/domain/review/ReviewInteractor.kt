package ru.ls.donkitchen.domain.review

import io.reactivex.Single
import ru.ls.donkitchen.core.domain.model.ReviewModel

interface ReviewInteractor {

    fun getReviews(receiptId: Int): Single<List<ReviewModel>>

}