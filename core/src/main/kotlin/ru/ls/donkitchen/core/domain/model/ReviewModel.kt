package ru.ls.donkitchen.core.domain.model

import java.util.*

data class ReviewModel(
        val id: Int = 0,
        val date: Date? = null,
        val rating: Int = 0,
        val userName: String? = null,
        val comments: String? = null)