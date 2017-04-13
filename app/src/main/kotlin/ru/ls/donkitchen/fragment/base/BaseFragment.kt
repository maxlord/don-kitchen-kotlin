package ru.ls.donkitchen.fragment.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import kotlinx.android.synthetic.main.widget_toolbar.*
import ru.ls.donkitchen.activity.base.BaseActivity
import ru.ls.donkitchen.activity.base.BaseNoActionBarActivity

/**
 *
 * @author Lord (Kuleshov M.V.)
 * @since 11.01.16
 */
abstract class BaseFragment: MvpAppCompatFragment() {
//    lateinit var prefs: SharedPreferences
//    @Inject
//    fun setSharedPreferences(@ConfigPrefs prefs: SharedPreferences) {
//        this.prefs = prefs
//    }

    private var component: FragmentSubComponent? = null

    fun getComponent(): FragmentSubComponent {
        return component!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutRes(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity.let { it ->
            if (it is BaseActivity) {
                this.component = it.getComponent().plus(FragmentModule(this))
                inject()
            } else if (it is BaseNoActionBarActivity) {
                this.component = it.getComponent().plus(FragmentModule(this))

                inject()
            }
        }

        if (toolbar != null) {
            toolbar.title = activity.title
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
