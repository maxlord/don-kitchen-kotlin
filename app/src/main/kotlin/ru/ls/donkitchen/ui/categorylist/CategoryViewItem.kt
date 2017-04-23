package ru.ls.donkitchen.ui.categorylist

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 10.04.17
 */
data class CategoryViewItem(val id: Int = 0,
                            val name: String? = null,
                            val imageLink: String? = null,
                            val receiptCount: Int,
                            val priority: Int = 0)
