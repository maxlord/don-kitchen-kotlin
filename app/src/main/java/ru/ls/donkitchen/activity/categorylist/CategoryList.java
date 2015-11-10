package ru.ls.donkitchen.activity.categorylist;

import android.support.v4.app.Fragment;

import ru.ls.donkitchen.activity.base.BaseActivity;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 12.10.15
 */
public class CategoryList extends BaseActivity {
	@Override
	protected Fragment loadFragment() {
		return CategoryListFragment.newInstance() ;
	}
}
