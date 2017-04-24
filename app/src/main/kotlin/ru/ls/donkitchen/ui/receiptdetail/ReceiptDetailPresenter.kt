package ru.ls.donkitchen.ui.receiptdetail

import com.arellomobile.mvp.InjectViewState
import io.reactivex.Observable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import ru.ls.donkitchen.BuildConfig
import ru.ls.donkitchen.R
import ru.ls.donkitchen.activity.base.SchedulersFactory
import ru.ls.donkitchen.domain.receipt.ReceiptInteractor
import ru.ls.donkitchen.mvp.BasePresenter
import ru.ls.donkitchen.ui.categorylist.ReceiptViewItemConverter
import javax.inject.Inject

@InjectViewState
class ReceiptDetailPresenter(
        private val receiptId: Int,
        component: ReceiptDetailSubComponent
) : BasePresenter<ReceiptDetailView>() {
    @Inject lateinit var interactor: ReceiptInteractor
    @Inject lateinit var schedulers: SchedulersFactory
    @Inject lateinit var viewItemConverter: ReceiptViewItemConverter
    @Inject lateinit var bus: RxBus

    init {
        component.inject(this)
    }

    override fun onFirstViewAttach() {
        viewState.setToolbarTitle("")
        if (!BuildConfig.DEBUG) {
            disposables += interactor.incrementReceiptViews(receiptId)
                    .observeOn(schedulers.ui())
                    .subscribeOn(schedulers.io())
                    .subscribeBy()
        }
        viewState.initPager(receiptId)
        disposables += bus.reviewCreateEvents()
                .observeOn(schedulers.ui())
                .subscribeBy(onNext = {
                    addReview()
                })
    }

    fun upClicks(observable: Observable<Unit>) {
        disposables += observable.subscribeBy(
                onNext = {
                    viewState.leaveScreen()
                }
        )
    }

    fun toolbarClicks(observable: Observable<Int>) {
        disposables += observable.subscribeBy(
                onNext = {
                    when (it) {
                        R.id.action_rate -> addReview()
                        R.id.action_share -> shareReceipt()
                    }
                }
        )
    }

    private fun addReview() {
        viewState.displayNewRatingDialog(receiptId)
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