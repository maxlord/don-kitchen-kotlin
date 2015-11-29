package ru.ls.donkitchen.activity.receiptlist;


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
import ru.ls.donkitchen.activity.receiptdetail.ReceiptDetail;
import ru.ls.donkitchen.annotation.IOScheduler;
import ru.ls.donkitchen.annotation.UIScheduler;
import ru.ls.donkitchen.app.DonKitchenApplication;
import ru.ls.donkitchen.base.BaseFragment;
import ru.ls.donkitchen.db.DatabaseHelper;
import ru.ls.donkitchen.db.table.Category;
import ru.ls.donkitchen.db.table.Receipt;
import ru.ls.donkitchen.helper.ActivityHelper;
import ru.ls.donkitchen.rest.Api;
import ru.ls.donkitchen.rest.model.response.ReceiptListResult;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import timber.log.Timber;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 22.09.15
 */
public class ReceiptListFragment extends BaseFragment implements Observer<List<ReceiptListResult.ReceiptItem>> {
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

	private ReceiptAdapter adapter;
	private int categoryId;
	private String categoryName;

	public static ReceiptListFragment newInstance(int categoryId, String categoryName) {
		Bundle args = new Bundle();
		args.putInt(ReceiptList.EXT_IN_CATEGORY_ID, categoryId);
		args.putString(ReceiptList.EXT_IN_CATEGORY_NAME, categoryName);

		ReceiptListFragment fragment = new ReceiptListFragment();
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_receipt_list;
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

		adapter = new ReceiptAdapter(getActivity(), recyclerView);
		recyclerView.setAdapter(adapter);

		final ItemClickSupport itemClick = ItemClickSupport.addTo(recyclerView);

		itemClick.setOnItemClickListener((parent, child, position, id) -> {
			ReceiptListResult.ReceiptItem item = ((ReceiptAdapter) parent.getAdapter()).getItem(position);

			Bundle b = new Bundle();
			b.putInt(ReceiptDetail.EXT_IN_RECEIPT_ID, item.id);
			b.putString(ReceiptDetail.EXT_IN_RECEIPT_NAME, item.name);

			ActivityHelper.startActivity(activity, ReceiptDetail.class, false, b);
		});
	}

	@Override
	protected void loadData() {
		if (getArguments() != null) {
			categoryId = getArguments().getInt(ReceiptList.EXT_IN_CATEGORY_ID, 0);
			categoryName = getArguments().getString(ReceiptList.EXT_IN_CATEGORY_NAME);
		}

		if (toolbar != null) {
			activity.setSupportActionBar(toolbar);

			activity.initDrawer(toolbar);

			activity.getDrawer().getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
			activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			toolbar.setNavigationOnClickListener(view -> activity.onBackPressed());
			toolbar.setTitle(categoryName);
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
		Timber.e(e, "Ошибка при загрузке рецептов");

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
	public void onNext(List<ReceiptListResult.ReceiptItem> result) {
		Timber.i("Обновляем UI");

		refreshData(result);
	}

	private void refreshData(List<ReceiptListResult.ReceiptItem> result) {
		try {
			if (activity != null && result != null) {
				progress.setVisibility(View.GONE);

				if (result.isEmpty()) {
					recyclerView.setVisibility(View.GONE);
					empty.setVisibility(View.VISIBLE);
				} else {
					adapter.clear();
					adapter.addAllItems(result);
					adapter.notifyDataSetChanged();

					recyclerView.setVisibility(View.VISIBLE);
					empty.setVisibility(View.GONE);
				}
			}
		} catch (Exception e) {
			Timber.e(e, "Ошибка получения списка рецептов");
		}
	}

	private void reloadData() {
		// Обновляем список рецептов
		// сеть
		Observable<List<ReceiptListResult.ReceiptItem>> network = api.getReceipts(categoryId)
				.map(result -> result.receipts);
		// сохранение/обновление в БД из сети
		Observable<List<ReceiptListResult.ReceiptItem>> networkWithSave = network.doOnNext(data -> {
			Timber.i("Сохраняем рецепты в БД");

			if (data != null && !data.isEmpty()) {
				try {
					Dao<Category, Integer> categoryDao = databaseHelper.getDao(Category.class);
					Dao<Receipt, Integer> receiptDao = databaseHelper.getDao(Receipt.class);

					for (ReceiptListResult.ReceiptItem ri : data) {
						// Получаем категорию
						Category receiptCategory = categoryDao.queryForId(ri.categoryId);

						Receipt item = new Receipt();
						item.id = ri.id;
						item.name = ri.name;
						item.ingredients = ri.ingredients;
						item.receipt = ri.receipt;
						item.imageLink = ri.imageLink;
						item.category = receiptCategory;
						item.viewsCount = ri.views;
						item.rating = ri.rating;

						receiptDao.createOrUpdate(item);
					}
				} catch (SQLException e) {
					Timber.e(e, "Ошибка сохранения рецептов в БД");
				}
			}
		});
		// БД
		Observable<List<ReceiptListResult.ReceiptItem>> db = Observable.create(s -> {
			Timber.i("Получаем рецепты из БД");

			try {
				Dao<Receipt, Integer> dao = databaseHelper.getDao(Receipt.class);
				QueryBuilder<Receipt, Integer> qb = dao.queryBuilder();
				qb.where().eq(Receipt.CATEGORY_ID, categoryId);
				qb.orderBy(Receipt.RATING, false);
				qb.orderBy(Receipt.VIEWS_COUNT, false);
				qb.orderBy(Receipt.NAME, true);
				List<Receipt> receipts = qb.query();
				List<ReceiptListResult.ReceiptItem> receiptItems = new ArrayList<>();
				if (!receipts.isEmpty()) {
					// Выполняем перепаковку объектов
					for (Receipt r : receipts) {
						ReceiptListResult.ReceiptItem item = new ReceiptListResult.ReceiptItem();

						item.id = r.id;
						item.name = r.name;
						item.ingredients = r.ingredients;
						item.receipt = r.receipt;
						item.views = r.viewsCount;
						item.categoryId = r.category.id;
						item.categoryName = r.category.name;
						item.imageLink = r.imageLink;
						item.rating = r.rating;

						receiptItems.add(item);
					}
				}

				s.onNext(receiptItems);

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
