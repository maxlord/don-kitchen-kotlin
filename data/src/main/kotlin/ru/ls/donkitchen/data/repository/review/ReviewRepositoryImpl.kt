package ru.ls.donkitchen.data.repository.review

import io.reactivex.Single
import ru.ls.donkitchen.core.data.entity.ReviewEntity
import ru.ls.donkitchen.core.data.repository.ReviewRepository
import ru.ls.donkitchen.data.rest.Api

class ReviewRepositoryImpl(
        private val api: Api,
        private val converter: ReviewEntityConverter) : ReviewRepository {

    override fun getReviews(receiptId: Int): Single<List<ReviewEntity>> {
        return api.getReviewsByReceipt(receiptId).map { it.reviews ?: listOf() }
                .map {
                    it.map { converter.convert(it) }
                }
    }

    override fun addReview(receiptId: Int, rating: Int, userName: String, comments: String): Single<Unit> {
        return api.addReview(receiptId, rating, userName, comments).map { Unit }
    }

}