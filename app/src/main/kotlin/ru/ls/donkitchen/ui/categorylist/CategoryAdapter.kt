package ru.ls.donkitchen.ui.categorylist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item_category.view.*
import ru.ls.donkitchen.R

/**
 * Адаптер для отображения списка категорий
 *
 * @author Lord (Kuleshov M.V.)
 * @since 16.10.16
 */
class CategoryAdapter(private val context: Context, private val callback: Callback) :
	androidx.recyclerview.widget.RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
	interface Callback {
		fun onItemClick(item: CategoryViewItem)
	}

	class ViewHolder(view: View, adapter: CategoryAdapter) :
		androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
		init {
			view.setOnClickListener {
				adapter.callback.onItemClick(it?.tag as CategoryViewItem)
			}
		}
	}

	private val items: MutableList<CategoryViewItem>?

	init {
		this.items = arrayListOf()
	}

	fun clear() {
		this.items!!.clear()
	}

	fun addAllItems(items: List<CategoryViewItem>) {
		this.items!!.addAll(items)
	}

	fun getItem(position: Int): CategoryViewItem? {
		if (items != null && position < items.size) {
			return items[position]
		}

		return null
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutInflater.from(context).inflate(R.layout.list_item_category, parent, false)

		return ViewHolder(view, this)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = items!![position]

		holder.itemView.tag = item

		holder.itemView.title.text = item.name
		holder.itemView.count.text = "${item.receiptCount}"

		Glide.with(context)
			.load(item.imageLink)
			.fitCenter()
			.centerCrop()
			.into(holder.itemView.photo)

		val lp =
			holder.itemView.layoutParams as androidx.recyclerview.widget.StaggeredGridLayoutManager.LayoutParams
		lp.isFullSpan = position == itemCount - 1 && (position + 1) % 2 == 1
		holder.itemView.layoutParams = lp
	}

	override fun getItemCount(): Int {
		return items!!.size
	}
}
