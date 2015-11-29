package ru.ls.donkitchen.activity.receiptdetail;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rey.material.widget.ProgressView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.lucasr.twowayview.widget.TwoWayView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.ls.donkitchen.R;
import ru.ls.donkitchen.activity.base.BaseActivity;
import ru.ls.donkitchen.activity.receiptdetail.event.ReviewAddedEvent;
import ru.ls.donkitchen.annotation.IOScheduler;
import ru.ls.donkitchen.annotation.UIScheduler;
import ru.ls.donkitchen.app.DonKitchenApplication;
import ru.ls.donkitchen.base.BaseFragment;
import ru.ls.donkitchen.db.DatabaseHelper;
import ru.ls.donkitchen.rest.Api;
import ru.ls.donkitchen.rest.model.response.ReviewListResult;
import rx.Observer;
import rx.Scheduler;
import timber.log.Timber;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 22.09.15
 */
public class ReceiptDetailReviewsFragment extends BaseFragment implements Observer<ReviewListResult> {
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

	@Inject
	Bus bus;

	int receiptId;
	private ReviewAdapter adapter;

	public static ReceiptDetailReviewsFragment newInstance(int receiptId) {
		Bundle args = new Bundle();

		args.putInt(ReceiptDetail.EXT_IN_RECEIPT_ID, receiptId);

		ReceiptDetailReviewsFragment fragment = new ReceiptDetailReviewsFragment();
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onPause() {
		super.onPause();

		bus.unregister(this);
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_receipt_detail_reviews;
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

		adapter = new ReviewAdapter(getActivity(), recyclerView);
		recyclerView.setAdapter(adapter);
	}

	@Override
	protected void loadData() {
		if (getArguments() != null) {
			receiptId = getArguments().getInt(ReceiptDetail.EXT_IN_RECEIPT_ID, 0);
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		bus.register(this);
		reloadData();
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
	public void onNext(ReviewListResult result) {
		Timber.i("Обновляем UI");

		refreshData(result);
	}

	private void refreshData(ReviewListResult result) {
		try {
			if (activity != null && result != null) {
				progress.setVisibility(View.GONE);

				if (result.reviews != null && !result.reviews.isEmpty()) {
					adapter.clear();
					adapter.addAllItems(result.reviews);
					adapter.notifyDataSetChanged();

					recyclerView.setVisibility(View.VISIBLE);
					empty.setVisibility(View.GONE);
				} else {
					recyclerView.setVisibility(View.GONE);
					empty.setVisibility(View.VISIBLE);
				}
			}
		} catch (Exception e) {
			Timber.e(e, "Ошибка получения списка категорий");
		}
	}

	private void reloadData() {
		api.getReviewsByReceipt(receiptId)
				.observeOn(ui)
				.subscribeOn(io)
				.subscribe(this);
	}

	@Subscribe
	public void onReload(ReviewAddedEvent event) {
		reloadData();
	}
}
