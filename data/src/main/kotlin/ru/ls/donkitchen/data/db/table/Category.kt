package ru.ls.donkitchen.data.db.table

import android.provider.BaseColumns
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 16.10.16
 */
@DatabaseTable(tableName = "category")
class Category : BaseColumns {
    @DatabaseField(id = true, canBeNull = false, columnName = BaseColumns._ID)
    var id: Int = 0
    @DatabaseField(canBeNull = false, columnName = NAME)
    var name: String? = null
    @DatabaseField(canBeNull = false, columnName = IMAGE_LINK)
    var imageLink: String? = null
    @DatabaseField(columnName = RECEIPT_COUNT)
    var receiptCount: Int = 0
    @DatabaseField(columnName = PRIORITY)
    var priority: Int = 0

    companion object {
        const val NAME = "name"
        const val IMAGE_LINK = "image_link"
        const val RECEIPT_COUNT = "receipt_count"
        const val PRIORITY = "priority"
    }

    override fun toString(): String {
        return "Category(id=$id, name=$name, imageLink=$imageLink, receiptCount=$receiptCount, priority=$priority)"
    }
}
