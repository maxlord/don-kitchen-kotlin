package ru.ls.donkitchen.activity.receiptdetail

import android.app.Fragment
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.v13.app.FragmentPagerAdapter
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.view.View
import com.bumptech.glide.Glide
import com.squareup.otto.Bus
import com.squareup.otto.Subscribe
import kotlinx.android.synthetic.main.dialog_new_review.view.*
import kotlinx.android.synthetic.main.fragment_receipt_detail.*
import kotlinx.android.synthetic.main.widget_toolbar.*
import org.jetbrains.anko.appcompat.v7.navigationIconResource
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import ru.ls.donkitchen.BuildConfig
import ru.ls.donkitchen.R
import ru.ls.donkitchen.activity.base.BaseActivity
import ru.ls.donkitchen.activity.base.SchedulersManager
import ru.ls.donkitchen.activity.receiptdetail.event.AddReviewEvent
import ru.ls.donkitchen.app.DonKitchenApplication
import ru.ls.donkitchen.data.rest.Api
import ru.ls.donkitchen.data.storage.ormlite.DatabaseHelper
import ru.ls.donkitchen.fragment.base.BaseFragment
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import javax.inject.Inject

/**
 * Просмотр рецепта
 *
 * @author Lord (Kuleshov M.V.)
 * @since 17.10.16
 */
class ReceiptDetailFragment : BaseFragment() {
    @Inject lateinit var application: DonKitchenApplication
    @Inject lateinit var activity: BaseActivity
    @Inject lateinit var api: Api
    @Inject lateinit var schedulersManager: SchedulersManager
    @Inject lateinit var bus: Bus
    @Inject lateinit var databaseHelper: DatabaseHelper

    private var receiptId: Int = 0
    private var receiptName: String = ""
    private var receiptPhotoUrl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onPause() {
        super.onPause()

        bus.unregister(this)
    }

    override fun onResume() {
        super.onResume()

        bus.register(this)
    }

    @Subscribe
    fun eventAddReview(event: AddReviewEvent) {
        addReview()
    }

    private fun addReview() {
        val dv = activity.layoutInflater.inflate(R.layout.dialog_new_review, null)

        AlertDialog.Builder(activity)
                .setTitle(R.string.activity_receipt_detail_dialog_add_review_title)
                .setView(dv)
                .setPositiveButton(R.string.common_ok, { dialog, which ->
                    val rating = dv.rating_bar.rating.toInt()

                    var uname = ""
                    if (!dv.user_name.text.toString().isNullOrBlank()) {
                        uname = dv.user_name.text.toString()
                    }

                    var comment = ""
                    if (!dv.comments.text.toString().isNullOrBlank()) {
                        comment = dv.comments.text.toString()
                    }

//                    api.addReview(receiptId, rating, uname, comment)
//                            .compose(schedulersManager.applySchedulers<ReviewResult>())
//                            .subscribeWith {
//                                onNext {
//                                    if (it != null) {
//                                        bus.post(ReviewAddedEvent())
//
//                                        AlertDialog.Builder(activity)
//                                                .setTitle(R.string.activity_receipt_detail_dialog_title_review_added_success)
//                                                .setMessage(R.string.activity_receipt_detail_dialog_review_added_success)
//                                                .setPositiveButton(R.string.common_ok, null)
//                                                .create()
//                                                .show()
//                                    }
//                                }
//                            }
                })
                .setNegativeButton(R.string.common_cancel, null)
                .create()
                .show()
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_receipt_detail
    }

    override fun inject() {
        getComponent().inject(this)
    }

    override fun initControls(v: View?) {
        super.initControls(v)

        toolbar?.navigationIconResource = R.drawable.ic_arrow_back
        toolbar?.setNavigationOnClickListener { activity.onBackPressed() }
        toolbar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_rate -> addReview()
                R.id.action_share -> shareReceipt()
            }

            true
        }
        toolbar?.inflateMenu(R.menu.menu_receipt_detail)
    }

    private fun shareReceipt() {
        var intent = activity.packageManager.getLaunchIntentForPackage(BuildConfig.INSTAGRAM_PKG_ID)
        if (intent != null) {
            var imageUri: Uri? = null
            doAsync {
                try {
                    val bytes = ByteArrayOutputStream()
                    val bm = Glide
                            .with(activity)
                            .load(receiptPhotoUrl)
                            .asBitmap()
                            .into(500, 500)
                            .get()
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

                    val folder = File(activity.filesDir, "image")
                    val imageFile = File(folder, "image")
                    if (imageFile.exists())
                        imageFile.delete()

                    imageFile.writeBytes(bytes.toByteArray())

                    imageUri = FileProvider.getUriForFile(activity, BuildConfig.FILE_PROVIDER_AUTHORITY, imageFile)

                    // Разрешаем доступ для Instagram
                    activity.grantUriPermission(BuildConfig.INSTAGRAM_PKG_ID, imageUri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION)

                } catch (e: FileNotFoundException) {
                    Timber.e(e, "Ошибка публикации рецепта в Instagram")
                }

                uiThread {
                    if (imageUri != null) {
                        val shareIntent = Intent()
                        shareIntent.action = Intent.ACTION_SEND
                        shareIntent.`package` = BuildConfig.INSTAGRAM_PKG_ID
                        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri!!)

                        shareIntent.type = "image/*"

                        startActivity(shareIntent)
                    }
                }
            }
        } else {
            // bring user to the market to download the app.
            // or let them choose an app?
            intent = Intent(Intent.ACTION_VIEW)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.data = Uri.parse("market://details?id=${BuildConfig.INSTAGRAM_PKG_ID}")
            startActivity(intent)
        }
    }

    override fun loadData() {
        pager.adapter = object : FragmentPagerAdapter(fragmentManager) {
            override fun getItem(position: Int): Fragment? {
                when (position) {
                    0 -> return ReceiptDetailInfoFragment.newInstance(receiptId)
                    1 -> return ReceiptDetailReviewsFragment.newInstance(receiptId)
                }

                return null
            }

            override fun getPageTitle(position: Int): CharSequence {
                when (position) {
                    0 -> return resources.getString(R.string.activity_receipt_detail_tab_info)

                    1 -> return resources.getString(R.string.activity_receipt_detail_tab_reviews)
                }

                return super.getPageTitle(position)
            }

            override fun getCount(): Int {
                return 2
            }
        }

        tablayout.setupWithViewPager(pager)

        if (arguments != null) {
            receiptId = arguments.getInt(ReceiptDetail.EXT_IN_RECEIPT_ID, 0)
            receiptName = arguments.getString(ReceiptDetail.EXT_IN_RECEIPT_NAME)
            receiptPhotoUrl = arguments.getString(ReceiptDetail.EXT_IN_RECEIPT_PHOTO_URL)

            toolbar?.title = receiptName

            if (receiptId > 0) {
                // Увеличиваем счетчик просмотров
                if (!BuildConfig.DEBUG) {
//                    api.incrementReceiptViews(receiptId, ReceiptIncrementViews())
//                            .compose(schedulersManager.applySchedulers<ReceiptDetailResult>())
//                            .subscribeWith {
//                                onNext {
//
//                                }
//
//                                onError {
//                                    Timber.e(it, "Ошибка увеличения количества просмотров")
//                                }
//                            }
                }
            }
        }
    }

    companion object {

        fun newInstance(receiptId: Int, receiptName: String, receiptPhotoUrl: String): ReceiptDetailFragment {
            val args = Bundle()
            args.putInt(ReceiptDetail.EXT_IN_RECEIPT_ID, receiptId)
            args.putString(ReceiptDetail.EXT_IN_RECEIPT_NAME, receiptName)
            args.putString(ReceiptDetail.EXT_IN_RECEIPT_PHOTO_URL, receiptPhotoUrl)

            val fragment = ReceiptDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
