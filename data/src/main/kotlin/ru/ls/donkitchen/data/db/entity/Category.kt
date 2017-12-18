package ru.ls.donkitchen.data.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "category")
class Category {
    @PrimaryKey
    var id: Int = 0
    var name: String = ""
    var imageLink: String = ""
    var receiptCount: Int = 0
    var priority: Int = 0

//    companion object {
//        const val NAME = "name"
//        const val IMAGE_LINK = "image_link"
//        const val RECEIPT_COUNT = "receipt_count"
//        const val PRIORITY = "priority"
//    }
}