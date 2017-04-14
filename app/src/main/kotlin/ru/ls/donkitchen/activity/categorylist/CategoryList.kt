package ru.ls.donkitchen.activity.categorylist

import android.os.Bundle
import ru.ls.donkitchen.activity.base.BaseActivity

/**
 * Список категорий
 *
 * @author Lord (Kuleshov M.V.)
 * @since 17.10.16
 */
class CategoryList: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun loadFragment() = CategoryListFragment.newInstance()
}
