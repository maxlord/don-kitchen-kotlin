package ru.ls.donkitchen.ui.receiptlist

import android.os.Bundle
import ru.ls.donkitchen.activity.base.BaseNoActionBarActivity

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 17.10.16
 */
class ReceiptList : BaseNoActionBarActivity() {
    companion object {
        const val EXT_IN_CATEGORY_ID = "category_id"
        const val EXT_IN_CATEGORY_NAME = "category_name"
    }

    var categoryId: Int = 0
    var categoryName: String = ""

    override fun loadFragment() = ReceiptListFragment.newInstance(categoryId, categoryName)

    override fun readArguments(args: Bundle) {
        categoryId = args.getInt(EXT_IN_CATEGORY_ID, 0)
        categoryName = args.getString(EXT_IN_CATEGORY_NAME)
    }
}
