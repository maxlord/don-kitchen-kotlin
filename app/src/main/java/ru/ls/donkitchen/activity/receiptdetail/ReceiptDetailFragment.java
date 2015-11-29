package ru.ls.donkitchen.activity.receiptdetail;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.ls.donkitchen.BuildConfig;
import ru.ls.donkitchen.R;
import ru.ls.donkitchen.activity.base.BaseActivity;
import ru.ls.donkitchen.activity.receiptdetail.event.AddReviewEvent;
import ru.ls.donkitchen.activity.receiptdetail.event.ReviewAddedEvent;
import ru.ls.donkitchen.annotation.IOScheduler;
import ru.ls.donkitchen.annotation.UIScheduler;
import ru.ls.donkitchen.app.DonKitchenApplication;
import ru.ls.donkitchen.base.BaseFragment;
import ru.ls.donkitchen.db.DatabaseHelper;
import ru.ls.donkitchen.rest.Api;
import ru.ls.donkitchen.rest.model.request.ReceiptIncrementViews;
import ru.ls.donkitchen.rest.model.response.ReceiptDetailResult;
import ru.ls.donkitchen.rest.model.response.ReviewResult;
import rx.Observer;
import rx.Scheduler;
import timber.log.Timber;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 13.10.15
 */
public class ReceiptDetailFragment extends BaseFragment {
	@Bind(R.id.tablayout)
	TabLayout tablayout;
	@Bind(R.id.pager)
	ViewPager pager;

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
	String receiptName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_receipt_detail, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_rate) {
			addReview();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onPause() {
		super.onPause();

		bus.unregister(this);
	}

	@Override
	public void onResume() {
		super.onResume();

		bus.register(this);
	}

	@Subscribe
	public void eventAddReview(AddReviewEvent event) {
		addReview();
	}

	private void addReview() {
		new MaterialDialog.Builder(getActivity())
				.title(R.string.activity_receipt_detail_dialog_add_review_title)
				.customView(R.layout.dialog_new_review, false)
				.positiveText(R.string.common_ok)
				.onPositive((dialog, dialogAction) -> {
					RatingBar dialogRatingBar = ButterKnife.findById(dialog, R.id.rating_bar);
					EditText userName = ButterKnife.findById(dialog, R.id.user_name);
					EditText comments = ButterKnife.findById(dialog, R.id.comments);

					int rating1 = (int) dialogRatingBar.getRating();

					String uname = "";
					if (!StringUtils.isEmpty(userName.getText())) {
						uname = userName.getText().toString();
					}

					String comment = "";
					if (!StringUtils.isEmpty(comments.getText())) {
						comment = comments.getText().toString();
					}

					api.addReview(receiptId, rating1, uname, comment)
							.observeOn(ui)
							.subscribeOn(io)
							.subscribe(new Observer<ReviewResult>() {
								@Override
								public void onCompleted() {

								}

								@Override
								public void onError(Throwable e) {

								}

								@Override
								public void onNext(ReviewResult ratingResult) {
									if (ratingResult != null) {
										bus.post(new ReviewAddedEvent());

										new MaterialDialog.Builder(getActivity())
												.title(R.string.activity_receipt_detail_dialog_title_review_added_success)
												.content(R.string.activity_receipt_detail_dialog_review_added_success)
												.positiveText(R.string.common_ok)
												.show();
									}
								}
							});
				})
				.negativeText(R.string.common_cancel)
				.show();
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_receipt_detail;
	}

	@Override
	protected void inject() {
		getComponent().inject(this);
	}

	public static ReceiptDetailFragment newInstance(int receiptId, String receiptName) {
		Bundle args = new Bundle();
		args.putInt(ReceiptDetail.EXT_IN_RECEIPT_ID, receiptId);
		args.putString(ReceiptDetail.EXT_IN_RECEIPT_NAME, receiptName);

		ReceiptDetailFragment fragment = new ReceiptDetailFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected void initControls(View v) {
		super.initControls(v);
	}

	@Override
	protected void loadData() {
		if (toolbar != null) {
			activity.setSupportActionBar(toolbar);

			activity.initDrawer(toolbar);
			activity.getDrawer().keyboardSupportEnabled(activity, true);

			activity.getDrawer().getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
			activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			toolbar.setNavigationOnClickListener(view -> activity.onBackPressed());
		}

		pager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
			@Override
			public android.support.v4.app.Fragment getItem(int position) {
				switch (position) {
					case 0:
						return ReceiptDetailInfoFragment.newInstance(receiptId);
					case 1:
						return ReceiptDetailReviewsFragment.newInstance(receiptId);
				}

				return null;
			}

			@Override
			public CharSequence getPageTitle(int position) {
				switch (position) {
					case 0:
						return getResources().getString(R.string.activity_receipt_detail_tab_info);

					case 1:
						return getResources().getString(R.string.activity_receipt_detail_tab_reviews);
				}

				return super.getPageTitle(position);
			}

			@Override
			public int getCount() {
				return 2;
			}
		});

		tablayout.setupWithViewPager(pager);

		if (getArguments() != null) {
			receiptId = getArguments().getInt(ReceiptDetail.EXT_IN_RECEIPT_ID, 0);
			receiptName = getArguments().getString(ReceiptDetail.EXT_IN_RECEIPT_NAME);

			toolbar.setTitle(receiptName);

			if (receiptId > 0) {
				// Увеличиваем счетчик просмотров
				if (!BuildConfig.DEBUG) {
					api.incrementReceiptViews(receiptId, new ReceiptIncrementViews())
							.observeOn(ui)
							.subscribeOn(io)
							.subscribe(new Observer<ReceiptDetailResult>() {
								@Override
								public void onCompleted() {

								}

								@Override
								public void onError(Throwable e) {
									Timber.e(e, "Ошибка увеличения количества просмотров");
								}

								@Override
								public void onNext(ReceiptDetailResult receiptDetailResult) {

								}
							});
				}
			}
		}
	}
}
