package ru.ls.donkitchen.activity.receiptdetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;
import org.lucasr.twowayview.widget.TwoWayView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.ls.donkitchen.R;
import ru.ls.donkitchen.rest.model.response.ReviewListResult;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 16.06.2015
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
	public static class ViewHolder extends RecyclerView.ViewHolder {
		@Bind(R.id.date)
		TextView date;
		@Bind(R.id.rating)
		RatingBar rating;
		@Bind(R.id.author)
		TextView author;
		@Bind(R.id.comments)
		TextView comments;

		public ViewHolder(View view) {
			super(view);

			ButterKnife.bind(this, view);
		}
	}

	private final Context context;
	private final TwoWayView recyclerView;
	private final List<ReviewListResult.ReviewItem> items;

	public ReviewAdapter(Context context, TwoWayView recyclerView) {
		super();

		this.context = context;
		this.recyclerView = recyclerView;
		this.items = new ArrayList<>();
	}

	public void clear() {
		this.items.clear();
	}

	public void addAllItems(List<ReviewListResult.ReviewItem> items) {
		this.items.addAll(items);
	}

	public ReviewListResult.ReviewItem getItem(int position) {
		if (items != null && position < items.size()) {
			return items.get(position);
		}

		return null;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		final View view = LayoutInflater.from(context).inflate(R.layout.list_item_review, parent, false);

		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		ReviewListResult.ReviewItem item = items.get(position);

		holder.date.setText(item.date.toString("dd.MM.yyyy"));
		holder.rating.setRating(item.rating);
		holder.author.setText(item.userName);
		if (StringUtils.isEmpty(item.userName)) {
			holder.author.setVisibility(View.GONE);
		} else {
			holder.author.setVisibility(View.VISIBLE);
		}
		holder.comments.setText(item.comments);
		if (StringUtils.isEmpty(item.comments)) {
			holder.comments.setVisibility(View.GONE);
		} else {
			holder.comments.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public int getItemCount() {
		return items.size();
	}
}
