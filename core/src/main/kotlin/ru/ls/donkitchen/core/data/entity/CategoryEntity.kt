package ru.ls.donkitchen.core.data.entity

data class CategoryEntity(
        val id: Int = 0,
        val name: String? = null,
        val imageLink: String? = null,
        val receiptCount: Int = 0,
        val priority: Int = 0
)
