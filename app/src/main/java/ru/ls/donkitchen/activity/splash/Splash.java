package ru.ls.donkitchen.activity.splash;

import android.support.v4.app.Fragment;

import ru.ls.donkitchen.activity.base.BaseActivity;

/**
 * Сплеш-скрин
 */
public class Splash extends BaseActivity {
	@Override
	protected Fragment loadFragment() {
		return SplashFragment.newInstance();
	}

	@Override
	public void onBackPressed() {

	}
}
