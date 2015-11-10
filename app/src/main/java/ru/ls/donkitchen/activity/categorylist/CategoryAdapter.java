package ru.ls.donkitchen.activity.categorylist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.lucasr.twowayview.widget.TwoWayView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.ls.donkitchen.R;
import ru.ls.donkitchen.rest.model.response.CategoryListResult;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 16.06.2015
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
	public static class ViewHolder extends RecyclerView.ViewHolder {
		@Bind(R.id.title)
		TextView title;
		@Bind(R.id.count)
		TextView count;

		public ViewHolder(View view) {
			super(view);

			ButterKnife.bind(this, view);
		}
	}

	private final Context context;
	private final TwoWayView recyclerView;
	private final List<CategoryListResult.CategoryItem> items;

	public CategoryAdapter(Context context, TwoWayView recyclerView) {
		super();

		this.context = context;
		this.recyclerView = recyclerView;
		this.items = new ArrayList<>();
	}

	public void clear() {
		this.items.clear();
	}

	public void addAllItems(List<CategoryListResult.CategoryItem> items) {
		this.items.addAll(items);
	}

	public CategoryListResult.CategoryItem getItem(int position) {
		if (items != null && position < items.size()) {
			return items.get(position);
		}

		return null;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		final View view = LayoutInflater.from(context).inflate(R.layout.list_item_category, parent, false);

		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		CategoryListResult.CategoryItem item = items.get(position);

		holder.title.setText(item.name);
		holder.count.setText(String.valueOf(item.receiptCount));
	}

	@Override
	public int getItemCount() {
		return items.size();
	}
}
