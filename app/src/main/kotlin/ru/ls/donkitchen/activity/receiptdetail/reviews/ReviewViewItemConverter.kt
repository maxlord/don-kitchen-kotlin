package ru.ls.donkitchen.activity.receiptdetail.reviews

import ru.ls.donkitchen.core.domain.model.ReviewModel

interface ReviewViewItemConverter {
    fun convert(item: ReviewModel): ReviewViewItem
}

class ReviewViewItemConverterImpl: ReviewViewItemConverter {

    override fun convert(item: ReviewModel): ReviewViewItem {
        return ReviewViewItem(
                date = item.date,
                rating = item.rating,
                userName = item.userName,
                comments = item.comments
        )
    }

}
