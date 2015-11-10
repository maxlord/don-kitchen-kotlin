package ru.ls.donkitchen.app;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import org.joda.time.DateTime;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import ru.ls.donkitchen.BuildConfig;
import ru.ls.donkitchen.annotation.IOScheduler;
import ru.ls.donkitchen.annotation.UIScheduler;
import ru.ls.donkitchen.rest.Api;
import ru.ls.donkitchen.rest.converter.DateTimeDeserializer;
import ru.ls.donkitchen.util.AsyncBus;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Модуль приложения
 *
 * @author Lord (Kuleshov M.V.)
 * @since 01.04.2015
 */
@Module
public class AppModule {
	protected final DonKitchenApplication application;

	public AppModule(DonKitchenApplication application) {
		this.application = application;

		Timber.plant(new Timber.DebugTree());
	}

	@Provides
	@Singleton
	DonKitchenApplication provideApplication() {
		return application;
	}

	@Provides
	@Singleton
	public Bus provideBus() {
		return new AsyncBus(ThreadEnforcer.ANY);
	}

	@Provides
	@UIScheduler
	@Singleton
	public Scheduler provideUiScheduler() {
		return AndroidSchedulers.mainThread();
	}

	@Provides
	@IOScheduler
	@Singleton
	public Scheduler provideIoScheduler() {
		return Schedulers.io();
	}

	@Provides
	@Singleton
	public Gson providerGson() {
		return new GsonBuilder()
				.registerTypeAdapter(DateTime.class, new DateTimeDeserializer())
				.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
				.create();
	}

	@Provides
	@Singleton
	public Api provideApi(Gson gson) {
		RestAdapter.Builder b = new RestAdapter.Builder();

		b.setConverter(new GsonConverter(gson));

		if (BuildConfig.DEBUG) {
			b.setLogLevel(RestAdapter.LogLevel.FULL);
		} else {
			b.setLogLevel(RestAdapter.LogLevel.FULL);
		}

		b.setRequestInterceptor(request -> {
//			request.addQueryParam("key", BuildConfig.PLACES_API_KEY);
//			request.addQueryParam("sensor", "false");
		});

		RestAdapter restAdapter = b.setEndpoint(BuildConfig.API_URL).build();
		return restAdapter.create(Api.class);
	}
}
