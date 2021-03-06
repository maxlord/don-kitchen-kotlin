package ru.ls.donkitchen.activity.base

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.MvpAppCompatFragment
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import ru.ls.donkitchen.R
import ru.ls.donkitchen.app.DonKitchenApplication
import javax.inject.Inject

abstract class BaseActivity : MvpAppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_mark_app -> navigateToGooglePlay()
        }
        val drawer = drawer_layout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun navigateToGooglePlay() {
        val appPackageName = packageName
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)))
        } catch (anfe: android.content.ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)))
        }
    }

    val FRAGMENT_TAG = "fragment_main"
    protected var fragment: Fragment? = null
    private lateinit var component: ActivitySubComponent
    fun component(): ActivitySubComponent {
        return component
    }

    @Inject lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application as DonKitchenApplication
        this.component = app.component().plus(ActivityModule(this))
        this.component.inject(this)

        setContentView(R.layout.activity_main)

        initControls()

//        setSupportActionBar(toolbar)

        val drawer = drawer_layout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = nav_view
        navigationView.setNavigationItemSelectedListener(this)

        val args = intent.extras
        if (args != null) {
            readArguments(args)
        }

        if (savedInstanceState == null) {
            fragment = loadFragment()

            if (fragment != null) {
                supportFragmentManager.beginTransaction().replace(R.id.fragment, fragment, FRAGMENT_TAG).commit()
            }
        } else {
            fragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG) as MvpAppCompatFragment
        }

//        if (toolbar_title != null) {
//            toolbar_title.text = title
//        }
    }

    protected open fun initControls() {

    }

    /**
     * Инициализирует и возвращает фрагмент для отображения в активити.
     * Классы наследники должны переопределять этот метод и загружать фрагмент.

     * @return
     */
    protected abstract fun loadFragment(): Fragment

    /**
     * Используя этот метод нужно инициализироваь аргументы переданнные в активити, через
     * getIntent().getExtras();
     */
    protected open fun readArguments(args: Bundle) {

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//
//        // освобождаем ресурсы хелпера БД
//        //		OpenHelperManager.releaseHelper();
//    }

//    override fun onNavigationItemSelected(@NonNull item: MenuItem): Boolean {
//        // Handle navigation view item clicks here.
//        val id = item.itemId
//
//        when (id) {
//            R.id.nav_home -> {
//                displayHome()
//            }
//
//            R.id.nav_exit -> {
//                item.isChecked = false
//
//                AlertDialog.Builder(this)
//                        .setTitle(R.string.common_warning)
//                        .setMessage(R.string.dialog_confirm_exit)
//                        .setPositiveButton(R.string.dialog_button_logout, { dialog, i ->
//                            finish()
//                        })
//                        .setNegativeButton(R.string.common_cancel, null)
//                        .create()
//                        .show()
//            }
//        }
//
////        val drawer = drawer_layout
////        drawer.closeDrawer(GravityCompat.START)
//
//        return true
//    }

//    private fun displayHome() {
//        ActivityHelper.startActivity(this, CategoryList::class.java, true)
//    }

}