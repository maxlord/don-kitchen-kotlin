package ru.ls.donkitchen.activity.splash;

import android.os.Bundle;
import android.os.SystemClock;

import com.crashlytics.android.Crashlytics;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import io.fabric.sdk.android.Fabric;
import ru.ls.donkitchen.R;
import ru.ls.donkitchen.activity.categorylist.CategoryList;
import ru.ls.donkitchen.annotation.IOScheduler;
import ru.ls.donkitchen.annotation.UIScheduler;
import ru.ls.donkitchen.app.DonKitchenApplication;
import ru.ls.donkitchen.base.BaseFragment;
import ru.ls.donkitchen.helper.ActivityHelper;
import ru.ls.donkitchen.rest.Api;
import rx.Scheduler;

public class SplashFragment extends BaseFragment {

	@Inject
	DonKitchenApplication application;

	@Inject
	RxAppCompatActivity activity;

	@Inject
	Api api;

	@Inject
	@IOScheduler
	Scheduler io;

	@Inject
	@UIScheduler
	Scheduler ui;

	public static SplashFragment newInstance() {
		Bundle args = new Bundle();

		SplashFragment fragment = new SplashFragment();
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	protected void loadData() {
		new IntentLauncher().start();
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_splash;
	}

	@Override
	protected void inject() {
		getComponent().inject(this);
	}

	private class IntentLauncher extends Thread {
		@Override
		/**
		 * Sleep for some time and than start new activity.
		 */
		public void run() {
			// Crashlytics
			Fabric.with(activity, new Crashlytics());

//			// Инициализируем БД
//			try {
//				SQLiteDatabase db = activity.openOrCreateDatabase(DatabaseHelper.DATABASE_NAME, 0, null);
//				ContextHolder.setContext(activity);
//				Flyway flyway = new Flyway();
//				flyway.setDataSource("jdbc:sqlite:" + db.getPath(), "", "");
//				flyway.setInitOnMigrate(true);
//				flyway.migrate();
//				db.close();
//			} catch (Exception e) {
//				Timber.e(e, "Ошибка инициализации flyway");
//			}

			SystemClock.sleep(1000);

			startMainActivity();
		}
	}

	private void startMainActivity() {
		ActivityHelper.startActivity(activity, CategoryList.class, true);
	}
}
