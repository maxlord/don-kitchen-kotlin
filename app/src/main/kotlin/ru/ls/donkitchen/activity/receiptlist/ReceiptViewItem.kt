package ru.ls.donkitchen.activity.receiptlist

data class ReceiptViewItem(val id: Int = 0,
                        val categoryId: Int = 0,
                        val name: String? = null,
                        val imageLink: String? = null,
                        val ingredients: String? = null,
                        val receipt: String? = null,
                        val viewsCount: Int = 0,
                        val rating: Int = 0)