package ru.ls.donkitchen.activity.receiptlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.lucasr.twowayview.widget.StaggeredGridLayoutManager;
import org.lucasr.twowayview.widget.TwoWayView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.ls.donkitchen.R;
import ru.ls.donkitchen.rest.model.response.ReceiptListResult;
import timber.log.Timber;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 16.06.2015
 */
public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ViewHolder> {
	public static class ViewHolder extends RecyclerView.ViewHolder {
		@Bind(R.id.photo)
		ImageView photo;
		@Bind(R.id.title)
		TextView title;

		public ViewHolder(View view) {
			super(view);

			ButterKnife.bind(this, view);
		}
	}

	private final Context context;
	private final TwoWayView recyclerView;
	private final List<ReceiptListResult.ReceiptItem> items;

	public ReceiptAdapter(Context context, TwoWayView recyclerView) {
		super();

		this.context = context;
		this.recyclerView = recyclerView;
		this.items = new ArrayList<>();
	}

	public void clear() {
		this.items.clear();
	}

	public void addAllItems(List<ReceiptListResult.ReceiptItem> items) {
		this.items.addAll(items);
	}

	public ReceiptListResult.ReceiptItem getItem(int position) {
		try {
			if (items != null && position < items.size()) {
				return items.get(position);
			}
		} catch (Exception e) {
			Timber.e(e, "Индекс вне диапазона");
		}

		return null;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		final View view = LayoutInflater.from(context).inflate(R.layout.list_item_receipt, parent, false);

		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		ReceiptListResult.ReceiptItem item = items.get(position);

		Picasso.with(context)
				.load(item.imageLink)
				.fit()
				.centerCrop()
				.into(holder.photo);

		holder.title.setText(item.name);

		final StaggeredGridLayoutManager.LayoutParams lp =
				(StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
		if (position == getItemCount() - 1 && (position + 1) % 2 == 1) {
			// если это последний элемент и он один, растягиваем его на всю строку
			lp.span = 2;
		} else {
			lp.span = 1;
		}
		holder.itemView.setLayoutParams(lp);
	}

	@Override
	public int getItemCount() {
		return items.size();
	}
}
