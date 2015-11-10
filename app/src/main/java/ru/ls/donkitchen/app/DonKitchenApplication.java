package ru.ls.donkitchen.app;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.OkHttpClient;

import io.fabric.sdk.android.Fabric;

/**
 * Главный класс приложения
 *
 * @author Lord (Kuleshov M.V.)
 * @since 26.08.15
 */
public class DonKitchenApplication extends Application {
	private AppComponent applicationComponent;

	@Override
	public void onCreate() {
		super.onCreate();

		Stetho.initializeWithDefaults(this);

		OkHttpClient client = new OkHttpClient();
		client.networkInterceptors().add(new StethoInterceptor());

		Fabric.with(this, new Crashlytics());

		applicationComponent = DaggerAppComponent.builder()
				.appModule(new AppModule(this))
				.build();
		applicationComponent.inject(this);
	}

	public AppComponent component() {
		return applicationComponent;
	}
}
