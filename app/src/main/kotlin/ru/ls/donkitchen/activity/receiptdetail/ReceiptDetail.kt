package ru.ls.donkitchen.activity.receiptdetail

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatFragment
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
        const val EXT_IN_RECEIPT_PHOTO_URL = "receipt_photo_url"
    }

    var receiptId: Int = 0
    var receiptName: String = ""
    var receiptPhotoUrl: String = ""

    override fun loadFragment() = ReceiptDetailFragment.newInstance(receiptId, receiptName, receiptPhotoUrl)

    override fun readArguments(args: Bundle) {
        receiptId = args.getInt(EXT_IN_RECEIPT_ID, 0)
        receiptName = args.getString(EXT_IN_RECEIPT_NAME)
        receiptPhotoUrl = args.getString(EXT_IN_RECEIPT_PHOTO_URL)
    }
}
