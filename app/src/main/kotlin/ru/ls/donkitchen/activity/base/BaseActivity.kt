package ru.ls.donkitchen.activity.base

import android.os.Bundle
import android.support.v4.app.Fragment
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.MvpAppCompatFragment
import ru.ls.donkitchen.R
import ru.ls.donkitchen.app.DonKitchenApplication

/**
 *
 * @author Lord (Kuleshov M.V.)
 * @since 11.01.16
 */
abstract class BaseActivity : MvpAppCompatActivity() {
    val FRAGMENT_TAG = "fragment_main"
    protected var fragment: Fragment? = null
    private lateinit var component: ActivitySubComponent
    fun getComponent(): ActivitySubComponent {
        return component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application as DonKitchenApplication
        this.component = app.component().plus(ActivityModule(this))
        this.component.inject(this)

        setContentView(R.layout.activity_main)

        initControls()

//        setSupportActionBar(toolbar)

//        val drawer = drawer_layout
//        val toggle = ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
//        drawer.addDrawerListener(toggle)
//        toggle.syncState()
//
//        val navigationView = nav_view
//        navigationView.setNavigationItemSelectedListener(this)

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
