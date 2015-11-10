package ru.ls.donkitchen.activity.receiptlist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import ru.ls.donkitchen.activity.base.BaseActivity;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 12.10.15
 */
public class ReceiptList extends BaseActivity {
	public static final String EXT_IN_CATEGORY_ID = "category_id";
	public static final String EXT_IN_CATEGORY_NAME = "category_name";

	int categoryId;
	String categoryName;

	@Override
	protected Fragment loadFragment() {
		return ReceiptListFragment.newInstance(categoryId, categoryName);
	}

	@Override
	protected void readArguments(@NonNull Bundle args) {
		categoryId = args.getInt(EXT_IN_CATEGORY_ID, 0);
		categoryName = args.getString(EXT_IN_CATEGORY_NAME);
	}
}
