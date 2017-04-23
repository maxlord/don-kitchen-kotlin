package ru.ls.donkitchen.fragment.base

import android.content.SharedPreferences
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatDialogFragment
import ru.ls.donkitchen.annotation.ConfigPrefs
import javax.inject.Inject

abstract class BaseDialogFragment: MvpAppCompatDialogFragment() {
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

        loadData()
    }

    /**
     * @return
     */
    @LayoutRes protected abstract fun getLayoutRes(): Int

    override fun onViewCreated(v: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)

        initControls(v)
        onRestoreInstanceState(savedInstanceState)
    }

    protected open fun onRestoreInstanceState(savedInstanceState: Bundle?) {

    }

    protected open fun initControls(v: View?) {

    }

    open fun loadData() {

    }
}
