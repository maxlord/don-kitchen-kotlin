package ru.ls.donkitchen.activity.receiptdetail

import android.app.Fragment
import android.os.Bundle
import ru.ls.donkitchen.activity.base.BaseActivity

/**
 *  Просмотр рецепта
 *
 * @author Lord (Kuleshov M.V.)
 * @since 17.10.16
 */
class ReceiptDetail: BaseActivity() {
    companion object {
        const val EXT_IN_RECEIPT_ID = "receipt_id"
        const val EXT_IN_RECEIPT_NAME = "receipt_name"
    }

    var receiptId: Int = 0
    var receiptName: String = ""

    override fun loadFragment(): Fragment {
        return ReceiptDetailFragment.newInstance(receiptId, receiptName)
    }

    override fun readArguments(args: Bundle) {
        receiptId = args.getInt(EXT_IN_RECEIPT_ID, 0);
        receiptName = args.getString(EXT_IN_RECEIPT_NAME);
    }
}