package ru.ls.donkitchen.activity.receiptdetail

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import com.bumptech.glide.Glide
import com.j256.ormlite.dao.Dao
import com.squareup.otto.Bus
import kotlinx.android.synthetic.main.fragment_receipt_detail_info.*
import org.jetbrains.anko.onClick
import ru.ls.donkitchen.R
import ru.ls.donkitchen.activity.receiptdetail.event.AddReviewEvent
import ru.ls.donkitchen.app.DonKitchenApplication
import ru.ls.donkitchen.data.rest.Api
import ru.ls.donkitchen.data.storage.ormlite.DatabaseHelper
import ru.ls.donkitchen.data.db.table.Receipt
import ru.ls.donkitchen.fragment.base.BaseFragment
import timber.log.Timber
import javax.inject.Inject

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 17.10.16
 */
class ReceiptDetailInfoFragment : BaseFragment() {
    @Inject lateinit var application: DonKitchenApplication
    @Inject lateinit var api: Api
    @Inject lateinit var bus: Bus
    @Inject lateinit var databaseHelper: DatabaseHelper

    private var receiptId: Int = 0
    private var receiptItem: Receipt? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_receipt_detail_info
    }

    override fun inject() {
        getComponent().inject(this)
    }

    override fun onPause() {
        bus.unregister(this)

        super.onPause()
    }

    override fun onResume() {
        super.onResume()

        bus.register(this)
    }

    override fun initControls(v: View?) {
        super.initControls(v)

        container_ingredients.visibility = View.GONE
        container.visibility = View.GONE
        progress.visibility = View.VISIBLE

        rate_button.onClick {
            bus.post(AddReviewEvent())
        }
    }

    override fun loadData() {
        if (arguments != null) {
            receiptId = arguments.getInt(ReceiptDetail.EXT_IN_RECEIPT_ID, 0)

            if (receiptId > 0) {
                try {
                    val dao = databaseHelper.getDao<Dao<Receipt, Int>, Receipt>(Receipt::class.java)

                    val r = dao.queryForId(receiptId)

                    if (r != null) {
                        receiptItem = r

                        try {
                            if (activity != null) {
                                progress.visibility = View.GONE
                                container.visibility = View.VISIBLE

                                Glide.with(activity).load(r.imageLink).fitCenter().centerCrop().into(icon)

                                views.text = r.viewsCount.toString()
                                rating.rating = r.rating.toFloat()

                                title!!.text = r.name

                                if (!r.ingredients.isNullOrEmpty()) {
                                    ingredients.text = r.ingredients
                                    container_ingredients.visibility = View.VISIBLE
                                } else {
                                    container_ingredients.visibility = View.GONE
                                }

                                receipt!!.text = r.receipt
                            }
                        } catch (e: Exception) {
                            Timber.e(e, "Ошибка во время получения рецепта")
                        }

                    }
                } catch (e: Exception) {
                    Timber.e(e, "Ошибка получения информации о рецепте")

                    progress!!.visibility = View.GONE

                    AlertDialog.Builder(activity)
                            .setTitle(R.string.common_info)
                            .setMessage(R.string.activity_receipt_detail_dialog_error_loading_receipt)
                            .setPositiveButton(R.string.common_ok, { dialog, i ->
                                activity.finish()
                            })
                            .setCancelable(false)
                            .create()
                            .show()
                }
            }
        }
    }

    companion object {

        fun newInstance(receiptId: Int): ReceiptDetailInfoFragment {
            val args = Bundle()
            args.putInt(ReceiptDetail.EXT_IN_RECEIPT_ID, receiptId)

            val fragment = ReceiptDetailInfoFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
