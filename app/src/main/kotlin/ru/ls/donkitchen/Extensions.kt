package ru.ls.donkitchen

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatFragment

/**
 * Класс функций-расширений
 *
 * @author Lord (Kuleshov M.V.)
 * @since 26.03.16
 */

fun Double.format(digits: Int) = java.lang.String.format("%.${digits}f", this)

fun Int.dip() = this * Resources.getSystem().displayMetrics.density.toInt()

/**
 * Открывает новую активити с возможностью закрытия текущей
 */
inline fun <reified T : Activity> Activity.navigate(close: Boolean = false, args: Bundle? = null) {
    val intent = Intent(this, T::class.java)
    if (args != null) {
        intent.putExtras(args)
    }
    startActivity(intent)
    if (close) {
        finish()
    }
}

/**
 * Открывае новую активити из фрагмента
 */
inline fun <reified T : Activity> MvpAppCompatFragment.navigateActivity(close: Boolean = false, args: Bundle? = null) {
    activity.navigate<T>(close, args)
}
