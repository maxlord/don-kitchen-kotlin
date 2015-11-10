package ru.ls.donkitchen.activity.base;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import ru.ls.donkitchen.R;
import ru.ls.donkitchen.annotation.ConfigPrefs;
import ru.ls.donkitchen.annotation.IOScheduler;
import ru.ls.donkitchen.annotation.UIScheduler;
import ru.ls.donkitchen.app.DonKitchenApplication;
import ru.ls.donkitchen.db.DatabaseHelper;
import ru.ls.donkitchen.rest.Api;
import rx.Scheduler;

public abstract class BaseActivity extends RxAppCompatActivity {
	public static final String FRAGMENT_TAG = "fragment_main";

	/**
	 * Пункт меню: Актуальные заявки
	 */
	public static final int DRAWER_ITEM_ACTUAL_ORDERS = 1;
	/**
	 * Пункт меню: Заявки в работе
	 */
	public static final int DRAWER_ITEM_WORK_ORDERS = 2;
	/**
	 * Пункт меню: Архивные заявки
	 */
	public static final int DRAWER_ITEM_ARCHIVE_ORDERS = 3;
	/**
	 * Пункт меню: Мой транспорт
	 */
	public static final int DRAWER_ITEM_MY_TRANSPORT = 4;
	/**
	 * Пункт меню: Мои ответы
	 */
	public static final int DRAWER_ITEM_MY_ANSWERS = 5;
	/**
	 * Пункт меню: Форум
	 */
	public static final int DRAWER_ITEM_FORUM = 6;
	/**
	 * Пункт меню: Профиль
	 */
	public static final int DRAWER_ITEM_PROFILE = 7;
	/**
	 * Пункт меню: Уведомления
	 */
	public static final int DRAWER_ITEM_NOTIFICATIONS = 8;
	/**
	 * Пункт меню: Листы
	 */
	public static final int DRAWER_ITEM_LISTS = 9;
	/**
	 * Пункт меню: Настройки
	 */
	public static final int DRAWER_ITEM_SETTINGS = 10;
	/**
	 * Пункт меню: Выход
	 */
	public static final int DRAWER_ITEM_LOGOUT = 11;
	/**
	 * Пункт меню: Карта
	 */
	public static final int DRAWER_ITEM_MAP = 12;
	/**
	 * Пункт меню: Новая заявка
	 */
	public static final int DRAWER_ITEM_NEW_ORDER = 13;
	/**
	 * Пункт меню: Добавить транспорт
	 */
	public static final int DRAWER_ITEM_ADD_VEHICLE = 14;

	@Inject
	DonKitchenApplication application;

	@Inject
	@ConfigPrefs
	SharedPreferences prefs;

	@Inject
	Api api;

	@Inject
	@UIScheduler
	Scheduler ui;

	@Inject
	@IOScheduler
	Scheduler io;

	@Inject
	DatabaseHelper databaseHelper;

	protected Fragment fragment;

	protected Drawer drawer;

	private ActivitySubComponent component;

	public ActivitySubComponent getComponent() {
		return component;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DonKitchenApplication app = (DonKitchenApplication) getApplication();
		this.component = app.component().plus(new ActivityModule(this));
		this.component.inject(this);

		setContentView(R.layout.activity_master);

		initControls();

		Bundle args = getIntent().getExtras();
		if (args != null) {
			readArguments(args);
		}

		if (savedInstanceState == null) {
			fragment = loadFragment();

			if (fragment != null) {
				getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment, FRAGMENT_TAG).commit();
			}
		} else {
			fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
		}
	}

	protected void initControls() {
		ButterKnife.bind(this);

//		setSupportActionBar(toolbar);
	}

	/**
	 * Инициализирует и возвращает фрагмент для отображения в активити.
	 * Классы наследники должны переопределять этот метод и загружать фрагмент.
	 *
	 * @return
	 */
	protected abstract Fragment loadFragment();

	/**
	 * Используя этот метод нужно инициализироваь аргументы переданнные в активити, через
	 * getIntent().getExtras();
	 */
	protected void readArguments(@NonNull Bundle args) {

	}

	public void initDrawer(Toolbar toolbar) {
//		ProfileDrawerItem pdi = null;
//		AccountHeader headerResult = null;
//		if (application.getUser() != null) {
//			pdi = new ProfileDrawerItem()
//					.withName(application.getUser().getName())
//					.withEmail(PhoneHelper.formatPhone(application.getUser().getLogin()));
//
//			headerResult = new AccountHeaderBuilder()
//					.withActivity(this)
//					.withSelectionListEnabled(false)
//					.withHeaderBackground(new ColorDrawable(getResources().getColor(R.color.profile_background)))
//					.withProfileImagesVisible(false)
//					.withCompactStyle(true)
//					.withAlternativeProfileHeaderSwitching(false)
//					.addProfiles(pdi)
//					.build();
//		}

		DrawerBuilder builder = new DrawerBuilder()
				.withActivity(this)
				.withToolbar(toolbar)
				.withActionBarDrawerToggle(true)
				.withActionBarDrawerToggleAnimated(true);
//		if (headerResult != null) {
//			builder.withAccountHeader(headerResult);
//		}

//		if (application.getUser() != null) {
//			if (application.getUser().isDriver()) {
//				builder.addDrawerItems(
//						new SectionDrawerItem().withName(R.string.drawer_item_orders),
//						new PrimaryDrawerItem().withName(R.string.drawer_item_new_order).withIdentifier(DRAWER_ITEM_NEW_ORDER).withSelectable(false),
//						new PrimaryDrawerItem().withName(R.string.drawer_item_actual_orders).withIdentifier(DRAWER_ITEM_ACTUAL_ORDERS),
//						new PrimaryDrawerItem().withName(R.string.drawer_item_map).withIdentifier(DRAWER_ITEM_MAP),
//						new PrimaryDrawerItem().withName(R.string.drawer_item_my_replies).withIdentifier(DRAWER_ITEM_MY_ANSWERS),
//						new DividerDrawerItem(),
//						new PrimaryDrawerItem().withName(R.string.drawer_item_add_vehicle).withIdentifier(DRAWER_ITEM_ADD_VEHICLE).withSelectable(false),
////					new PrimaryDrawerItem().withName("Мой транспорт").withIdentifier(DRAWER_ITEM_MY_TRANSPORT),
////					new PrimaryDrawerItem().withName("Форум").withIdentifier(DRAWER_ITEM_FORUM),
////					new DividerDrawerItem(),
//						new PrimaryDrawerItem().withName(R.string.drawer_item_profile).withIdentifier(DRAWER_ITEM_PROFILE),
//						new PrimaryDrawerItem().withName(R.string.drawer_item_notifications).withIdentifier(DRAWER_ITEM_NOTIFICATIONS).withBadge(String.valueOf(notificationCount)),
//						new PrimaryDrawerItem().withName(R.string.drawer_item_settings).withIdentifier(DRAWER_ITEM_SETTINGS),
//						new DividerDrawerItem(),
//						new PrimaryDrawerItem().withName(R.string.drawer_item_logout).withIdentifier(DRAWER_ITEM_LOGOUT).withSelectable(false)
//				);
//			} else {
//				builder.addDrawerItems(
//						new SectionDrawerItem().withName(R.string.drawer_item_orders),
//						new PrimaryDrawerItem().withName(R.string.drawer_item_new_order).withIdentifier(DRAWER_ITEM_NEW_ORDER).withSelectable(false),
//						new PrimaryDrawerItem().withName(R.string.drawer_item_actual_orders).withIdentifier(DRAWER_ITEM_ACTUAL_ORDERS),
//						new PrimaryDrawerItem().withName(R.string.drawer_item_work_orders).withIdentifier(DRAWER_ITEM_WORK_ORDERS),
//						new PrimaryDrawerItem().withName(R.string.drawer_item_archive_orders).withIdentifier(DRAWER_ITEM_ARCHIVE_ORDERS),
//						new PrimaryDrawerItem().withName(R.string.drawer_item_map).withIdentifier(DRAWER_ITEM_MAP).withSelectable(false),
//						new DividerDrawerItem(),
//						new PrimaryDrawerItem().withName(R.string.drawer_item_lists).withIdentifier(DRAWER_ITEM_LISTS),
//						new PrimaryDrawerItem().withName(R.string.drawer_item_add_vehicle).withIdentifier(DRAWER_ITEM_ADD_VEHICLE).withSelectable(false),
//						new PrimaryDrawerItem().withName(R.string.drawer_item_profile).withIdentifier(DRAWER_ITEM_PROFILE),
//						new PrimaryDrawerItem().withName(R.string.drawer_item_notifications).withIdentifier(DRAWER_ITEM_NOTIFICATIONS).withBadge(String.valueOf(notificationCount)),
//						new PrimaryDrawerItem().withName(R.string.drawer_item_settings).withIdentifier(DRAWER_ITEM_SETTINGS),
//						new DividerDrawerItem(),
//						new PrimaryDrawerItem().withName(R.string.drawer_item_logout).withIdentifier(DRAWER_ITEM_LOGOUT).withSelectable(false)
//				);
//			}
//		}
//		builder.withOnDrawerItemClickListener((view, position, drawerItem) -> processItemClick(drawerItem.getIdentifier()));
		builder.withSelectedItem(-1);

		drawer = builder.build();
		drawer.keyboardSupportEnabled(this, false);
	}

//	private boolean processItemClick(int identifier) {
//		switch (identifier) {
//			case DRAWER_ITEM_NEW_ORDER:
//				ActivityHelper.startActivity(this, Order.class, false);
//				break;
//
//			case DRAWER_ITEM_ACTUAL_ORDERS:
//				ActivityHelper.startActivity(this, OrderList.class, true);
//				break;
//
//			case DRAWER_ITEM_WORK_ORDERS:
//				ActivityHelper.startActivity(this, WorkOrderList.class, true);
//				break;
//
//			case DRAWER_ITEM_ARCHIVE_ORDERS:
//				ActivityHelper.startActivity(this, ArchiveOrderList.class, true);
//				break;
//
//			case DRAWER_ITEM_MAP:
//				ActivityHelper.startActivity(this, MapActivity.class, false);
//				break;
//
//			case DRAWER_ITEM_MY_ANSWERS:
//				ActivityHelper.startActivity(this, MyAnswerList.class, true);
//				break;
//
//			case DRAWER_ITEM_PROFILE:
//				ActivityHelper.startActivity(this, Profile.class, true);
//				break;
//
//			case DRAWER_ITEM_NOTIFICATIONS:
//				ActivityHelper.startActivity(this, NotificationList.class, true);
//				break;
//
//			case DRAWER_ITEM_SETTINGS:
//				ActivityHelper.startActivity(this, Settings.class, true);
//				break;
//
//			case DRAWER_ITEM_LOGOUT:
//				new MaterialDialog.Builder(this)
//						.title(R.string.app_name)
//						.content("Вы действительно хотите выйти?")
//						.positiveText(R.string.common_logout)
//						.negativeText(R.string.common_cancel)
//						.callback(new MaterialDialog.ButtonCallback() {
//							@Override
//							public void onPositive(MaterialDialog dialog) {
//								SharedPreferences.Editor editor = prefs.edit();
//								editor.putBoolean(BuildConfig.SP_IS_LOGGED_OUT, true);
//								editor.apply();
//
//								ActivityHelper.startActivity(BaseActivity.this, Login.class, true);
//							}
//						})
//						.show();
//				break;
//
//			case DRAWER_ITEM_ADD_VEHICLE:
//				ActivityHelper.startActivity(this, Vehicle.class, false);
//				break;
//
//			case DRAWER_ITEM_LISTS:
//				ActivityHelper.startActivity(this, MyLists.class, true);
//				break;
//		}
//
//		return false;
//	}

	public Drawer getDrawer() {
		return drawer;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// освобождаем ресурсы хелпера БД
//		OpenHelperManager.releaseHelper();
	}
}
