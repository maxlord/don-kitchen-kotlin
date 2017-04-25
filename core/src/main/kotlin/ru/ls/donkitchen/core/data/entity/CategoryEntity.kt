package ru.ls.donkitchen.core.data.entity

data class CategoryEntity(
        val id: Int = 0,
        val name: String = "",
        val imageLink: String = "",
        val receiptCount: Int = 0,
        val priority: Int = 0
)
