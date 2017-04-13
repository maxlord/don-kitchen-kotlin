package ru.ls.donkitchen.core.data.entity

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 02.03.17
 */
data class CategoryEntity(
        val id: Int = 0,
        val name: String? = null,
        val imageLink: String? = null,
        val receiptCount: Int = 0,
        val priority: Int = 0
)
