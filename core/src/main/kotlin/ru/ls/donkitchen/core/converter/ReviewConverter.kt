package ru.ls.donkitchen.core.converter

import ru.ls.donkitchen.core.data.entity.ReviewEntity
import ru.ls.donkitchen.core.domain.model.ReviewModel

interface ReviewConverter {
    fun convert(entity: ReviewEntity): ReviewModel
}

class ReviewConverterImpl : ReviewConverter {
    override fun convert(entity: ReviewEntity): ReviewModel {
        with(entity) {
            return ReviewModel(id, date, rating, userName, comments)
        }
    }
}