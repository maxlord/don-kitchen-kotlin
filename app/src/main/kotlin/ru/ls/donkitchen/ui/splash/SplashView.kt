package ru.ls.donkitchen.ui.splash

import android.support.annotation.StringRes
import com.arellomobile.mvp.MvpView

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 02.04.17
 */
interface SplashView: MvpView {
    fun showLoading()

    fun startActivity()
}