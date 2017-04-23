package ru.ls.donkitchen.activity.receiptdetail.reviews

import java.util.*

data class ReviewViewItem(
        val date: Date? = null,
        val rating: Int = 0,
        val userName: String? = null,
        val comments: String? = null)