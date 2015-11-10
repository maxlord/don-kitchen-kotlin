package ru.ls.donkitchen.activity.receiptlist;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.rey.material.widget.ProgressView;

import org.lucasr.twowayview.ItemClickSupport;
import org.lucasr.twowayview.widget.TwoWayView;

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
import ru.ls.donkitchen.helper.ActivityHelper;
import ru.ls.donkitchen.rest.Api;
import ru.ls.donkitchen.rest.model.response.ReceiptListResult;
import rx.Observer;
import rx.Scheduler;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 22.09.15
 */
public class ReceiptListFragment extends BaseFragment implements Observer<ReceiptListResult> {
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

		adapter = new ReceiptAdapter(getActivity(), recyclerView);
		recyclerView.setAdapter(adapter);

		final ItemClickSupport itemClick = ItemClickSupport.addTo(recyclerView);

		itemClick.setOnItemClickListener((parent, child, position, id) -> {
			ReceiptListResult.ReceiptItem item = ((ReceiptAdapter) parent.getAdapter()).getItem(position);

			Bundle b = new Bundle();
			b.putInt(ReceiptDetail.EXT_IN_RECEIPT_ID, item.id);

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
		if (activity != null) {
			progress.setVisibility(View.GONE);

			recyclerView.setVisibility(View.GONE);
			empty.setVisibility(View.VISIBLE);

			empty.setText(e.getLocalizedMessage());
		}
	}

	@Override
	public void onNext(ReceiptListResult result) {
		if (activity != null && result != null) {
			progress.setVisibility(View.GONE);

			adapter.clear();
			adapter.addAllItems(result.receipts);
			adapter.notifyDataSetChanged();

			if (result.receipts.isEmpty()) {
				recyclerView.setVisibility(View.GONE);
				empty.setVisibility(View.VISIBLE);
			} else {
				recyclerView.setVisibility(View.VISIBLE);
				empty.setVisibility(View.GONE);
			}
		}
	}

	private void reloadData() {
		// Обновляем список категорий
		api.getReceipts(categoryId)
				.subscribeOn(io)
				.observeOn(ui)
				.subscribe(this);
	}
}
