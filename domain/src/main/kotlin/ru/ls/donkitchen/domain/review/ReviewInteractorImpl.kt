package ru.ls.donkitchen.domain.review

import io.reactivex.Single
import ru.ls.donkitchen.core.converter.ReviewConverter
import ru.ls.donkitchen.core.data.repository.ReviewRepository
import ru.ls.donkitchen.core.domain.model.ReviewModel


class ReviewInteractorImpl(private var repository: ReviewRepository,
                           private var converter: ReviewConverter): ReviewInteractor {

    override fun getReviews(receiptId: Int): Single<List<ReviewModel>> {
        return repository.getReviews(receiptId).map { it.map(converter::convert) }
    }

    override fun addReview(receiptId: Int, rating: Int, userName: String, comments: String): Single<Unit> {
        return repository.addReview(receiptId, rating, userName, comments)
    }

}