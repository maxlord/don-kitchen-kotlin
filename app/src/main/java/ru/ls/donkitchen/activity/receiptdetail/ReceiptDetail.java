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
	public static final String EXT_IN_RECEIPT_NAME = "receipt_name";

	int receiptId;
	String receiptName;

	@Override
	protected Fragment loadFragment() {
		return ReceiptDetailFragment.newInstance(receiptId, receiptName);
	}

	@Override
	protected void readArguments(@NonNull Bundle args) {
		receiptId = args.getInt(EXT_IN_RECEIPT_ID, 0);
		receiptName = args.getString(EXT_IN_RECEIPT_NAME);
	}
}
