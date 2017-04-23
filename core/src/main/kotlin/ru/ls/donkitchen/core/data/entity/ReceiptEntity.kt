package ru.ls.donkitchen.core.data.entity

data class ReceiptEntity(
        val id: Int = 0,
        val categoryId: Int = 0,
        val name: String = "",
        val imageLink: String? = null,
        val ingredients: String? = null,
        val receipt: String = "",
        val viewsCount: Int = 0,
        val rating: Int = 0)