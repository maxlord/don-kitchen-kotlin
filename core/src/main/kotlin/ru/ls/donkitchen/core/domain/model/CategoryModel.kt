package ru.ls.donkitchen.core.domain.model

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 02.03.17
 */
data class CategoryModel(val id: Int = 0,
                         val name: String = "",
                         val imageLink: String = "",
                         val receiptCount: Int,
                         val priority: Int = 0)