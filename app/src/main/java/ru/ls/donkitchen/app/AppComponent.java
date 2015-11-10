package ru.ls.donkitchen.app;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Component;
import ru.ls.donkitchen.activity.base.ActivityModule;
import ru.ls.donkitchen.activity.base.ActivitySubComponent;
import ru.ls.donkitchen.rest.Api;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
	void inject(DonKitchenApplication application);

	Bus provideBus();
	Api provideApi();

	ActivitySubComponent plus(ActivityModule module);
//	ServiceSubComponent plus(ServiceModule module);
}
