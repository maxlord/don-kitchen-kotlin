package ru.ls.donkitchen.core.data.repository

import io.reactivex.Single
import ru.ls.donkitchen.core.data.entity.ReviewEntity

interface ReviewRepository {
    fun getReviews(receiptId: Int): Single<List<ReviewEntity>>
    fun addReview(receiptId: Int, rating: Int, userName: String, comments: String): Single<Unit>
}