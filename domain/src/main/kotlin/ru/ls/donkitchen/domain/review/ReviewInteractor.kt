package ru.ls.donkitchen.domain.review

import io.reactivex.Single
import ru.ls.donkitchen.core.domain.model.ReviewModel

interface ReviewInteractor {

    fun getReviews(receiptId: Int): Single<List<ReviewModel>>

    fun addReview(receiptId: Int, rating: Int, userName: String, comments: String): Single<Unit>

}