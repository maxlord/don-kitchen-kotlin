package ru.ls.donkitchen.base;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxFragment;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.ls.donkitchen.R;
import ru.ls.donkitchen.activity.base.BaseActivity;
import ru.ls.donkitchen.annotation.ConfigPrefs;

/**
 * Базовый фрагмент
 *
 * @author Lord (Kuleshov M.V.)
 * @since 01.04.2015
 */
public abstract class BaseFragment extends RxFragment {
	@Nullable
	@Bind(R.id.toolbar)
	protected Toolbar toolbar;

	@Inject
	@ConfigPrefs
	SharedPreferences prefs;

	private FragmentSubComponent component;

	public FragmentSubComponent getComponent() {
		return component;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(getLayoutRes(), container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (getActivity() instanceof BaseActivity) {
			BaseActivity activity = (BaseActivity) getActivity();
			this.component = activity.getComponent().plus(new FragmentModule(this));

			inject();
		}

		if (toolbar != null) {
			toolbar.setTitle(getActivity().getTitle());
		}

		loadData();
	}

	/**
	 *
	 *
	 * @return
	 */
	protected abstract @LayoutRes int getLayoutRes();

	protected abstract void inject();

	@Override
	public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);

		initControls(v);
		onRestoreInstanceState(savedInstanceState);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();

		ButterKnife.unbind(this);
	}

	protected void onRestoreInstanceState(Bundle savedInstanceState) {

	}

	protected void initControls(View v) {
		ButterKnife.bind(this, v);
	}

	protected void loadData() {

	}

	/**
	 * Получение настроек пользователя
	 * @return
	 */
	protected SharedPreferences getPrefs() {
		return prefs;
	}

//	/**
//	 * Получает токен авторизации
//	 *
//	 * @return
//	 */
//	protected String getAuthToken() {
//		return getPrefs().getString(BuildConfig.SP_TOKEN, "");
//	}
}
