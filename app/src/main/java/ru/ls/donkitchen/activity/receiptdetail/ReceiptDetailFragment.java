package ru.ls.donkitchen.activity.receiptdetail;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rey.material.widget.ProgressView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;

import butterknife.Bind;
import ru.ls.donkitchen.R;
import ru.ls.donkitchen.activity.base.BaseActivity;
import ru.ls.donkitchen.annotation.IOScheduler;
import ru.ls.donkitchen.annotation.UIScheduler;
import ru.ls.donkitchen.app.DonKitchenApplication;
import ru.ls.donkitchen.base.BaseFragment;
import ru.ls.donkitchen.rest.Api;
import ru.ls.donkitchen.rest.model.response.ReceiptDetailResult;
import rx.Observer;
import rx.Scheduler;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 13.10.15
 */
public class ReceiptDetailFragment extends BaseFragment implements Observer<ReceiptDetailResult> {

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

	int receiptId;
	ReceiptDetailResult receiptItem;

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_receipt_detail;
	}

	@Override
	protected void inject() {
		getComponent().inject(this);
	}

	public static ReceiptDetailFragment newInstance(int receiptId) {
		Bundle args = new Bundle();
		args.putInt(ReceiptDetail.EXT_IN_RECEIPT_ID, receiptId);

		ReceiptDetailFragment fragment = new ReceiptDetailFragment();
		fragment.setArguments(args);
		return fragment;
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
		if (toolbar != null) {
			activity.setSupportActionBar(toolbar);

			activity.initDrawer(toolbar);
			activity.getDrawer().keyboardSupportEnabled(activity, true);

			activity.getDrawer().getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
			activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			toolbar.setNavigationOnClickListener(view -> activity.onBackPressed());
		}

		if (getArguments() != null) {
			receiptId = getArguments().getInt(ReceiptDetail.EXT_IN_RECEIPT_ID, 0);

			if (receiptId > 0) {
				api.getReceiptDetail(receiptId)
						.subscribeOn(io)
						.observeOn(ui)
						.subscribe(this);
			}
		}
	}

	@Override
	public void onCompleted() {

	}

	@Override
	public void onError(Throwable e) {
		if (activity != null) {
			progress.setVisibility(View.GONE);

			new MaterialDialog.Builder(activity)
					.content(R.string.activity_receipt_detail_dialog_error_loading_receipt)
					.positiveText(R.string.common_ok)
					.autoDismiss(false)
					.callback(new MaterialDialog.ButtonCallback() {
						@Override
						public void onPositive(MaterialDialog dialog) {
							super.onPositive(dialog);

							activity.finish();
						}
					})
					.show();
		}
	}

	@Override
	public void onNext(ReceiptDetailResult result) {
		if (activity != null) {
			progress.setVisibility(View.GONE);
			container.setVisibility(View.VISIBLE);

			if (result != null) {
				receiptItem = result;

				Picasso.with(activity).load(receiptItem.imageLink).into(icon);

				title.setText(receiptItem.name);

				if (!StringUtils.isEmpty(receiptItem.ingredients)) {
					ingredients.setText(receiptItem.ingredients);
					containerIngredients.setVisibility(View.VISIBLE);
				} else {
					containerIngredients.setVisibility(View.GONE);
				}

				receipt.setText(receiptItem.receipt);
			}
		}
	}
}
