package ru.ls.donkitchen.data.repository.review

import ru.ls.donkitchen.core.data.entity.ReviewEntity
import ru.ls.donkitchen.data.rest.response.ReviewListResult

interface ReviewEntityConverter {
    fun convert(item: ReviewListResult.ReviewItem): ReviewEntity
}

class ReviewEntityConverterImpl: ReviewEntityConverter {
    override fun convert(item: ReviewListResult.ReviewItem): ReviewEntity {
        return ReviewEntity(
                id = item.id,
                receiptId = item.receiptId,
                rating = item.rating,
                date = item.date,
                userName = item.userName,
                comments = item.comments
        )
    }

}