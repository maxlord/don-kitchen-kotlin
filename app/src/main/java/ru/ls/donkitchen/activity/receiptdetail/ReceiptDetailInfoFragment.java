package ru.ls.donkitchen.activity.receiptdetail;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.j256.ormlite.dao.Dao;
import com.rey.material.widget.ProgressView;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import ru.ls.donkitchen.R;
import ru.ls.donkitchen.activity.base.BaseActivity;
import ru.ls.donkitchen.activity.receiptdetail.event.AddReviewEvent;
import ru.ls.donkitchen.annotation.IOScheduler;
import ru.ls.donkitchen.annotation.UIScheduler;
import ru.ls.donkitchen.app.DonKitchenApplication;
import ru.ls.donkitchen.base.BaseFragment;
import ru.ls.donkitchen.db.DatabaseHelper;
import ru.ls.donkitchen.db.table.Receipt;
import ru.ls.donkitchen.rest.Api;
import rx.Scheduler;
import timber.log.Timber;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 13.10.15
 */
public class ReceiptDetailInfoFragment extends BaseFragment {

	@Bind(R.id.container)
	ScrollView container;
	@Bind(R.id.progress)
	ProgressView progress;
	@Bind(R.id.title)
	TextView title;
	@Bind(R.id.icon)
	ImageView icon;
	@Bind(R.id.ingredients)
	TextView ingredients;
	@Bind(R.id.container_ingredients)
	LinearLayout containerIngredients;
	@Bind(R.id.receipt)
	TextView receipt;
	@Bind(R.id.container_receipt)
	LinearLayout containerReceipt;
	@Bind(R.id.views)
	TextView views;
	@Bind(R.id.rating)
	RatingBar rating;


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
	Receipt receiptItem;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);
	}

	@OnClick(R.id.rate_button)
	public void actionRate() {
		bus.post(new AddReviewEvent());
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_receipt_detail_info;
	}

	@Override
	protected void inject() {
		getComponent().inject(this);
	}

	public static ReceiptDetailInfoFragment newInstance(int receiptId) {
		Bundle args = new Bundle();
		args.putInt(ReceiptDetail.EXT_IN_RECEIPT_ID, receiptId);

		ReceiptDetailInfoFragment fragment = new ReceiptDetailInfoFragment();
		fragment.setArguments(args);
		return fragment;
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

	@Override
	protected void initControls(View v) {
		super.initControls(v);

		containerIngredients.setVisibility(View.GONE);
		container.setVisibility(View.GONE);
		progress.setVisibility(View.VISIBLE);
	}

	@Override
	protected void loadData() {
		if (getArguments() != null) {
			receiptId = getArguments().getInt(ReceiptDetail.EXT_IN_RECEIPT_ID, 0);

			if (receiptId > 0) {
				try {
					Dao<Receipt, Integer> dao = databaseHelper.getDao(Receipt.class);

					Receipt r = dao.queryForId(receiptId);

					if (r != null) {
						receiptItem = r;

						try {
							if (activity != null) {
								progress.setVisibility(View.GONE);
								container.setVisibility(View.VISIBLE);

								Picasso.with(activity)
										.load(receiptItem.imageLink)
										.fit()
										.centerCrop()
										.into(icon);

								views.setText(String.valueOf(receiptItem.viewsCount));
								rating.setRating(receiptItem.rating);

								title.setText(receiptItem.name);

								if (!StringUtils.isEmpty(receiptItem.ingredients)) {
									ingredients.setText(receiptItem.ingredients);
									containerIngredients.setVisibility(View.VISIBLE);
								} else {
									containerIngredients.setVisibility(View.GONE);
								}

								receipt.setText(receiptItem.receipt);
							}
						} catch (Exception e) {
							Timber.e(e, "Ошибка во время получения рецепта");
						}
					}
				} catch (Exception e) {
					Timber.e(e, "Ошибка получения информации о рецепте");

					progress.setVisibility(View.GONE);

					new MaterialDialog.Builder(activity)
							.content(R.string.activity_receipt_detail_dialog_error_loading_receipt)
							.positiveText(R.string.common_ok)
							.autoDismiss(false)
							.onPositive((materialDialog, dialogAction) -> activity.finish())
							.show();
				}
			}
		}
	}
}
