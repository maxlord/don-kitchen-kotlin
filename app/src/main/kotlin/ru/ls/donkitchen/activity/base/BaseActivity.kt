package ru.ls.donkitchen.activity.base

import android.app.Fragment
import android.content.SharedPreferences
import android.os.Bundle
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import ru.ls.donkitchen.R
import ru.ls.donkitchen.annotation.ConfigPrefs
import ru.ls.donkitchen.app.DonKitchenApplication
import ru.ls.donkitchen.rest.Api
import javax.inject.Inject

/**
 *
 * @author Lord (Kuleshov M.V.)
 * @since 11.01.16
 */
abstract class BaseActivity : RxAppCompatActivity() {
    val FRAGMENT_TAG = "fragment_main"

    @Inject lateinit var app: DonKitchenApplication
    @Inject lateinit var api: Api
    @Inject lateinit var schedulersManager: SchedulersManager
    lateinit var prefs: SharedPreferences
    @Inject
    fun setSharedPreferences(@ConfigPrefs prefs: SharedPreferences) {
        this.prefs = prefs
    }

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
                fragmentManager.beginTransaction().replace(R.id.fragment, fragment, FRAGMENT_TAG).commit()
            }
        } else {
            fragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG)
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
