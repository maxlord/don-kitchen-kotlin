package ru.ls.donkitchen.ui.receiptdetail

import android.os.Bundle
import ru.ls.donkitchen.activity.base.BaseNoActionBarActivity
import ru.ls.donkitchen.app.DonKitchenApplication

/**
 *  Просмотр рецепта
 *
 * @author Lord (Kuleshov M.V.)
 * @since 17.10.16
 */
class ReceiptDetail: BaseNoActionBarActivity() {
    companion object {
        const val EXT_IN_RECEIPT_ID = "receipt_id"
        const val EXT_IN_RECEIPT_NAME = "receipt_name"
    }

    private var receiptId: Int = 0
    private var receiptName: String = ""
    private lateinit var componentCustom: ReceiptDetailActivitySubComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application as DonKitchenApplication
        componentCustom = app.component().plus(ReceiptDetailActivityModule(this))
    }

    override fun loadFragment() = ReceiptDetailFragment.newInstance(receiptId, receiptName)

    override fun readArguments(args: Bundle) {
        receiptId = args.getInt(EXT_IN_RECEIPT_ID, 0)
        receiptName = args.getString(EXT_IN_RECEIPT_NAME)
    }

    fun componentCustom(): ReceiptDetailActivitySubComponent {
        return componentCustom
    }

}