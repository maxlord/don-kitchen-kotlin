package ru.ls.donkitchen.data.db.table

import android.provider.BaseColumns
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import ru.ls.donkitchen.data.db.table.Category

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 16.10.16
 */
@DatabaseTable(tableName = "receipt")
class Receipt : BaseColumns {
    companion object {
        const val NAME = "name"
        const val INGREDIENTS = "ingredients"
        const val CATEGORY_ID = "category_id"
        const val RECEIPT = "receipt"
        const val IMAGE_LINK = "image_link"
        const val VIEWS_COUNT = "views_count"
        const val RATING = "rating"
    }

    @DatabaseField(id = true, canBeNull = false, columnName = BaseColumns._ID)
    var id: Int = 0
    @DatabaseField(canBeNull = false, foreign = true, columnName = CATEGORY_ID, foreignColumnName = BaseColumns._ID, foreignAutoRefresh = true)
    var category: Category? = null
    @DatabaseField(canBeNull = false, columnName = NAME)
    var name: String? = null
    @DatabaseField(canBeNull = false, columnName = IMAGE_LINK)
    var imageLink: String? = null
    @DatabaseField(columnName = INGREDIENTS)
    var ingredients: String? = null
    @DatabaseField(canBeNull = false, columnName = RECEIPT)
    var receipt: String? = null
    @DatabaseField(columnName = VIEWS_COUNT)
    var viewsCount: Int = 0
    @DatabaseField(columnName = RATING)
    var rating: Int = 0

    override fun toString(): String {
        return "Receipt(id=$id, category=$category, name=$name, imageLink=$imageLink, ingredients=$ingredients, receipt=$receipt, viewsCount=$viewsCount, rating=$rating)"
    }
}
