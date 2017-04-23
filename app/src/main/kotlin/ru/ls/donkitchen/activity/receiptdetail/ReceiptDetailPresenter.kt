package ru.ls.donkitchen.activity.receiptdetail

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import ru.ls.donkitchen.BuildConfig
import ru.ls.donkitchen.R
import ru.ls.donkitchen.activity.base.SchedulersFactory
import ru.ls.donkitchen.activity.categorylist.ReceiptViewItemConverter
import ru.ls.donkitchen.domain.receipt.ReceiptInteractor
import javax.inject.Inject

@InjectViewState
class ReceiptDetailPresenter(
        private val receiptId: Int,
        component: ReceiptDetailSubComponent
) : MvpPresenter<ReceiptDetailView>() {
    @Inject lateinit var interactor: ReceiptInteractor
    @Inject lateinit var schedulers: SchedulersFactory
    @Inject lateinit var viewItemConverter: ReceiptViewItemConverter

    init {
        component.inject(this)
    }

    override fun onFirstViewAttach() {
        viewState.setToolbarTitle("")
        if (!BuildConfig.DEBUG) {
            interactor.incrementReceiptViews(receiptId)
                    .observeOn(schedulers.ui())
                    .subscribeOn(schedulers.io())
                    .subscribeBy(
                            onSuccess = { },
                            onError = { }
                    )
        }
        viewState.initPager(receiptId)
    }

    fun upClicks(observable: Observable<Unit>) {
        observable
                .subscribeBy(
                        onNext = {
                            viewState.leaveScreen()
                        },
                        onError = {

                        }
                )
    }

    fun toolbarClicks(observable: Observable<Int>) {
        observable
                .subscribeBy(
                        onNext = {
                            when (it) {
                                R.id.action_rate -> addReview()
                                R.id.action_share -> shareReceipt()
                            }
                        },
                        onError = {

                        }
                )
    }

    private fun addReview() {
//        val dv = activity.layoutInflater.inflate(R.layout.dialog_new_review, null)
//
//        AlertDialog.Builder(activity)
//                .setTitle(R.string.activity_receipt_detail_dialog_add_review_title)
//                .setView(dv)
//                .setPositiveButton(R.string.common_ok, { dialog, which ->
//                    val rating = dv.rating_bar.rating.toInt()
//
//                    var uname = ""
//                    if (!dv.user_name.text.toString().isNullOrBlank()) {
//                        uname = dv.user_name.text.toString()
//                    }
//
//                    var comment = ""
//                    if (!dv.comments.text.toString().isNullOrBlank()) {
//                        comment = dv.comments.text.toString()
//                    }
//
////                    api.addReview(receiptId, rating, uname, comment)
////                            .compose(schedulersManager.applySchedulers<ReviewResult>())
////                            .subscribeWith {
////                                onNext {
////                                    if (it != null) {
////                                        bus.post(ReviewAddedEvent())
////
////                                        AlertDialog.Builder(activity)
////                                                .setTitle(R.string.activity_receipt_detail_dialog_title_review_added_success)
////                                                .setMessage(R.string.activity_receipt_detail_dialog_review_added_success)
////                                                .setPositiveButton(R.string.common_ok, null)
////                                                .create()
////                                                .show()
////                                    }
////                                }
////                            }
//                })
//                .setNegativeButton(R.string.common_cancel, null)
//                .create()
//                .show()
    }

    private fun shareReceipt() {
//        var intent = activity.packageManager.getLaunchIntentForPackage(BuildConfig.INSTAGRAM_PKG_ID)
//        if (intent != null) {
//            var imageUri: Uri? = null
//
////            doAsync {
//            try {
//                val bytes = ByteArrayOutputStream()
//                val bm = Glide
//                        .with(activity)
//                        .load("" /*receiptPhotoUrl*/)
//                        .asBitmap()
//                        .into(500, 500)
//                        .get()
//                bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
//
//                val folder = File(activity.filesDir, "image")
//                val imageFile = File(folder, "image")
//                if (imageFile.exists())
//                    imageFile.delete()
//
//                imageFile.writeBytes(bytes.toByteArray())
//
//                imageUri = FileProvider.getUriForFile(activity, BuildConfig.FILE_PROVIDER_AUTHORITY, imageFile)
//
//                // Разрешаем доступ для Instagram
//                activity.grantUriPermission(BuildConfig.INSTAGRAM_PKG_ID, imageUri,
//                        Intent.FLAG_GRANT_READ_URI_PERMISSION)
//
//            } catch (e: FileNotFoundException) {
//                Timber.e(e, "Ошибка публикации рецепта в Instagram")
//            }
//
////                uiThread {
//            if (imageUri != null) {
//                val shareIntent = Intent()
//                shareIntent.action = Intent.ACTION_SEND
//                shareIntent.`package` = BuildConfig.INSTAGRAM_PKG_ID
//                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri!!)
//
//                shareIntent.type = "image/*"
//
//                startActivity(shareIntent)
//            }
////                }
////            }
//        } else {
//            // bring user to the market to download the app.
//            // or let them choose an app?
//            intent = Intent(Intent.ACTION_VIEW)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            intent.data = Uri.parse("market://details?id=${BuildConfig.INSTAGRAM_PKG_ID}")
//            startActivity(intent)
//        }
    }
}