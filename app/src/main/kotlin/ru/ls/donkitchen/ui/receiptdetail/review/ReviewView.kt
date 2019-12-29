package ru.ls.donkitchen.ui.receiptdetail.review

import androidx.annotation.StringRes
import com.arellomobile.mvp.MvpView

interface ReviewView : MvpView {
    fun showTitle(@StringRes titleId: Int)
    fun showRating(ratingValue: Float)
    fun dismissDialog()
    fun displayReviewSuccessMessage()
}