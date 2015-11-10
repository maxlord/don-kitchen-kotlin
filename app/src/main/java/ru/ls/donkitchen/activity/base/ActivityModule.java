package ru.ls.donkitchen.activity.base;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import dagger.Module;
import dagger.Provides;
import ru.ls.donkitchen.annotation.ConfigPrefs;
import ru.ls.donkitchen.annotation.PerActivity;
import ru.ls.donkitchen.db.DatabaseHelper;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 15.05.2015
 */
@Module
public class ActivityModule {
	private final RxAppCompatActivity activity;

	public ActivityModule(RxAppCompatActivity activity) {
		this.activity = activity;
	}

	@Provides
	@PerActivity
	public RxAppCompatActivity provideActivity() {
		return activity;
	}

	@Provides
	@PerActivity
	public BaseActivity provideBaseActivity() {
		return (BaseActivity) activity;
	}

	@Provides
	@PerActivity
	public DatabaseHelper provideDatabaseHelper() {
		return OpenHelperManager.getHelper(activity, DatabaseHelper.class);
	}

	@Provides
	@ConfigPrefs
	@PerActivity
	public SharedPreferences provideConfigPrefs() {
		return PreferenceManager.getDefaultSharedPreferences(activity);
	}

	public void releaseDatabaseHelper() {
		OpenHelperManager.releaseHelper();
	}
}
