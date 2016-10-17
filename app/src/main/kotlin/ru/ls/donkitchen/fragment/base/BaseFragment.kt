package ru.ls.donkitchen.fragment.base

import android.content.SharedPreferences
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trello.rxlifecycle.components.RxFragment
import kotlinx.android.synthetic.main.widget_toolbar.*
import ru.ls.donkitchen.activity.base.BaseActivity
import ru.ls.donkitchen.activity.base.BaseNoActionBarActivity
import ru.ls.donkitchen.activity.splash.Splash
import ru.ls.donkitchen.annotation.ConfigPrefs
import javax.inject.Inject

/**
 *
 * @author Lord (Kuleshov M.V.)
 * @since 11.01.16
 */
abstract class BaseFragment: RxFragment() {
    lateinit var prefs: SharedPreferences
    @Inject
    fun setSharedPreferences(@ConfigPrefs prefs: SharedPreferences) {
        this.prefs = prefs
    }

    private var component: FragmentSubComponent? = null

    fun getComponent(): FragmentSubComponent {
        return component!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutRes(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (activity is BaseActivity) {
            val activity = activity as BaseActivity
            this.component = activity.getComponent().plus(FragmentModule(this))

            inject()
        } else if (activity is BaseNoActionBarActivity) {
            val activity = activity as BaseNoActionBarActivity
            this.component = activity.getComponent().plus(FragmentModule(this))

            inject()
        } else if (activity is Splash) {
            val activity = activity as Splash
            this.component = activity.getComponent().plus(FragmentModule(this))

            inject()
        }

        if (toolbar != null) {
            toolbar!!.title = activity.title
        }

        loadData()
    }

    /**
     * @return
     */
    @LayoutRes protected abstract fun getLayoutRes(): Int

    protected abstract fun inject()

    override fun onViewCreated(v: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)

        initControls(v)
        onRestoreInstanceState(savedInstanceState)
    }

    protected open fun onRestoreInstanceState(savedInstanceState: Bundle?) {

    }

    protected open fun initControls(v: View?) {

    }

    protected open fun loadData() {

    }

    protected fun releaseDatabaseHelper() {
//        OpenHelperManager.releaseHelper()
    }
}
