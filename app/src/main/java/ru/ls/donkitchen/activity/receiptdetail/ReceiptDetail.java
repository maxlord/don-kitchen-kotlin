package ru.ls.donkitchen.activity.receiptdetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import ru.ls.donkitchen.activity.base.BaseActivity;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 13.10.15
 */
public class ReceiptDetail extends BaseActivity {
	public static final String EXT_IN_RECEIPT_ID = "receipt_id";

	int receiptId;

	@Override
	protected Fragment loadFragment() {
		return ReceiptDetailFragment.newInstance(receiptId);
	}

	@Override
	protected void readArguments(@NonNull Bundle args) {
		receiptId = args.getInt(EXT_IN_RECEIPT_ID, 0);
	}
}
