package ru.ls.donkitchen.activity.base;

import dagger.Subcomponent;
import ru.ls.donkitchen.annotation.PerActivity;
import ru.ls.donkitchen.base.FragmentModule;
import ru.ls.donkitchen.base.FragmentSubComponent;

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivitySubComponent {
	FragmentSubComponent plus(FragmentModule module);

	void inject(BaseActivity activity);
}
