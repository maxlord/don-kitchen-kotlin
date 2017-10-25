package ru.ls.donkitchen.fragment.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import kotlinx.android.synthetic.main.toolbar.*
import ru.ls.donkitchen.activity.base.BaseActivity
import ru.ls.donkitchen.activity.base.BaseNoActionBarActivity
import ru.ls.donkitchen.nav.ActivityNavigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 *
 * @author Lord (Kuleshov M.V.)
 * @since 11.01.16
 */
abstract class BaseFragment: MvpAppCompatFragment() {
    @Inject lateinit var router: Router
    @Inject lateinit var navigatorHolder: NavigatorHolder

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutRes(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity.let { it ->
            if (it is BaseActivity) {
                it.component().inject(this)
            } else if (it is BaseNoActionBarActivity) {
                it.component().inject(this)
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

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(ActivityNavigator(activity))
    }

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
