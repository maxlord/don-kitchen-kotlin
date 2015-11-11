package ru.ls.donkitchen.activity.categorylist;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.rey.material.widget.ProgressView;

import org.lucasr.twowayview.ItemClickSupport;
import org.lucasr.twowayview.widget.TwoWayView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.ls.donkitchen.R;
import ru.ls.donkitchen.activity.base.BaseActivity;
import ru.ls.donkitchen.activity.receiptlist.ReceiptList;
import ru.ls.donkitchen.annotation.IOScheduler;
import ru.ls.donkitchen.annotation.UIScheduler;
import ru.ls.donkitchen.app.DonKitchenApplication;
import ru.ls.donkitchen.base.BaseFragment;
import ru.ls.donkitchen.db.DatabaseHelper;
import ru.ls.donkitchen.db.table.Category;
import ru.ls.donkitchen.helper.ActivityHelper;
import ru.ls.donkitchen.rest.Api;
import ru.ls.donkitchen.rest.model.response.CategoryListResult;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import timber.log.Timber;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 22.09.15
 */
public class CategoryListFragment extends BaseFragment implements Observer<List<CategoryListResult.CategoryItem>> {
	@Bind(R.id.list)
	TwoWayView recyclerView;

	@Bind(R.id.empty)
	TextView empty;

	@Bind(R.id.progress)
	ProgressView progress;

	@Inject
	DonKitchenApplication application;

	@Inject
	BaseActivity activity;

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

	private CategoryAdapter adapter;

	public static CategoryListFragment newInstance() {
		Bundle args = new Bundle();

		CategoryListFragment fragment = new CategoryListFragment();
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_category_list;
	}

	@Override
	protected void inject() {
		getComponent().inject(this);
	}

	@Override
	protected void initControls(View v) {
		ButterKnife.bind(this, v);

		setHasOptionsMenu(true);

		recyclerView.setHasFixedSize(true);

		adapter = new CategoryAdapter(getActivity(), recyclerView);
		recyclerView.setAdapter(adapter);

		final ItemClickSupport itemClick = ItemClickSupport.addTo(recyclerView);

		itemClick.setOnItemClickListener((parent, child, position, id) -> {
			CategoryListResult.CategoryItem item = ((CategoryAdapter) parent.getAdapter()).getItem(position);

			Bundle b = new Bundle();
			b.putInt(ReceiptList.EXT_IN_CATEGORY_ID, item.id);
			b.putString(ReceiptList.EXT_IN_CATEGORY_NAME, item.name);

			ActivityHelper.startActivity(activity, ReceiptList.class, false, b);
		});
	}

	@Override
	protected void loadData() {
		if (toolbar != null) {
			activity.setSupportActionBar(toolbar);

			activity.initDrawer(toolbar);

			activity.getDrawer().getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
//			activity.getDrawer().setSelection(BaseActivity.DRAWER_ITEM_ACTUAL_ORDERS, false);
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		reloadData();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//		inflater.inflate(R.menu.menu_order_list, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
//		int id = item.getItemId();
//		if (id == R.id.action_add) {
//			ActivityHelper.startActivity(activity, Order.class, false);
//			return true;
//		} else if (id == R.id.action_map) {
//			ActivityHelper.startActivity(activity, MapActivity.class, false);
//			return true;
//		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCompleted() {

	}

	@Override
	public void onError(Throwable e) {
		Timber.e(e, "Ошибка при загрузке категорий");

		if (activity != null) {
			progress.setVisibility(View.GONE);

			if (adapter.getItemCount() == 0) {
				recyclerView.setVisibility(View.GONE);
				empty.setVisibility(View.VISIBLE);
			} else {
				recyclerView.setVisibility(View.VISIBLE);
				empty.setVisibility(View.GONE);
			}
		}
	}

	@Override
	public void onNext(List<CategoryListResult.CategoryItem> result) {
		Timber.i("Обновляем UI");

		refreshData(result);
	}

	private void refreshData(List<CategoryListResult.CategoryItem> result) {
		try {
			if (activity != null && result != null) {
				progress.setVisibility(View.GONE);

				adapter.clear();
				adapter.addAllItems(result);
				adapter.notifyDataSetChanged();

				if (result.isEmpty()) {
					recyclerView.setVisibility(View.GONE);
					empty.setVisibility(View.VISIBLE);
				} else {
					recyclerView.setVisibility(View.VISIBLE);
					empty.setVisibility(View.GONE);
				}
			}
		} catch (Exception e) {
			Timber.e(e, "Ошибка получения списка категорий");
		}
	}

	private void reloadData() {
		// Обновляем список категорий
		// сеть
		Observable<List<CategoryListResult.CategoryItem>> network = api.getCategories()
				.map(result -> result.categories);
		// сохранение/обновление в БД из сети
		Observable<List<CategoryListResult.CategoryItem>> networkWithSave = network.doOnNext(data -> {
			Timber.i("Сохраняем категории в БД");

			if (data != null && !data.isEmpty()) {
				try {
					Dao<Category, Integer> dao = databaseHelper.getDao(Category.class);

					for (CategoryListResult.CategoryItem ci : data) {
						Category item = new Category();
						item.id = ci.id;
						item.name = ci.name;
						item.imageLink = ci.imageLink;
						item.priority = ci.priority;
						item.receiptCount = ci.receiptCount;

						dao.createOrUpdate(item);
					}
				} catch (SQLException e) {
					Timber.e(e, "Ошибка сохранения категорий в БД");
				}
			}
		});
		// БД
		Observable<List<CategoryListResult.CategoryItem>> db = Observable.create(s -> {
			Timber.i("Получаем категории из БД");

			try {
				Dao<Category, Integer> dao = databaseHelper.getDao(Category.class);
				QueryBuilder<Category, Integer> qb = dao.queryBuilder();
				qb.orderBy(Category.PRIORITY, true);
				List<Category> categories = qb.query();
				List<CategoryListResult.CategoryItem> categoryItems = new ArrayList<>();
				if (!categories.isEmpty()) {
					// Выполняем перепаковку объектов
					for (Category c : categories) {
						CategoryListResult.CategoryItem item = new CategoryListResult.CategoryItem();
						item.id = c.id;
						item.name = c.name;
						item.imageLink = c.imageLink;
						item.priority = c.priority;
						item.receiptCount = c.receiptCount;

						categoryItems.add(item);
					}
				}

				s.onNext(categoryItems);

				s.onCompleted();
			} catch (SQLException e) {
				s.onError(e);
			}
		});

		// Выполняем все запросы
		Observable.concat(db, networkWithSave)
				.subscribeOn(io)
				.observeOn(ui)
				.subscribe(this);
	}
}
