package ru.ls.donkitchen.base;

import android.support.v4.app.Fragment;

import com.mobsandgeeks.saripaar.Validator;

import dagger.Module;
import dagger.Provides;
import ru.ls.donkitchen.annotation.PerFragment;

@Module
public class FragmentModule {
	private final Fragment fragment;

	public FragmentModule(Fragment fragment) {
		this.fragment = fragment;
	}

	@Provides
	@PerFragment
	public Validator provideValidator() {
		return new Validator(fragment);
	}
}
