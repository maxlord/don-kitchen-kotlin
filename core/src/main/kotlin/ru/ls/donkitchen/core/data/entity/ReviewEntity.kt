package ru.ls.donkitchen.core.data.entity

import java.util.*

data class ReviewEntity(
        val id: Int = 0,
        val receiptId: Int = 0,
        val date: Date? = null,
        val rating: Int = 0,
        val userName: String? = null,
        val comments: String? = null)