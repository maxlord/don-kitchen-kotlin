package ru.ls.donkitchen.activity.receiptlist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item_receipt.view.*
import org.jetbrains.anko.onClick
import ru.ls.donkitchen.R
import ru.ls.donkitchen.rest.model.response.ReceiptListResult
import timber.log.Timber
import java.util.*

/**
 * Адаптер для отображения рецептов
 *
 * @author Lord (Kuleshov M.V.)
 * @since 17.10.16
 */
class ReceiptAdapter(private val context: Context, private val callback: Callback) : RecyclerView.Adapter<ReceiptAdapter.ViewHolder>() {
    interface Callback {
        fun onItemClick(item: ReceiptListResult.ReceiptItem)
    }

    class ViewHolder(view: View, adapter: ReceiptAdapter) : RecyclerView.ViewHolder(view) {
        init {
            view.onClick {
                adapter.callback.onItemClick(view.tag as ReceiptListResult.ReceiptItem)
            }
        }
    }

    private val items: MutableList<ReceiptListResult.ReceiptItem>?

    init {
        this.items = ArrayList<ReceiptListResult.ReceiptItem>()
    }

    fun clear() {
        this.items!!.clear()
    }

    fun addAllItems(items: List<ReceiptListResult.ReceiptItem>) {
        this.items!!.addAll(items)
    }

    fun getItem(position: Int): ReceiptListResult.ReceiptItem? {
        try {
            if (items != null && position < items.size) {
                return items[position]
            }
        } catch (e: Exception) {
            Timber.e(e, "Индекс вне диапазона")
        }

        return null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_receipt, parent, false)

        return ViewHolder(view, this)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items!![position]

        holder.itemView.tag = item

        Glide.with(context).load(item.imageLink).fitCenter().centerCrop().into(holder.itemView.photo)

        holder.itemView.title!!.text = item.name
        holder.itemView.views!!.text = item.views.toString()
        holder.itemView.rating!!.rating = item.rating.toFloat()

        val lp = holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
        if (position == itemCount - 1 && (position + 1) % 2 == 1) {
            // если это последний элемент и он один, растягиваем его на всю строку
            lp.isFullSpan = true
        } else {
            lp.isFullSpan = false
        }
        holder.itemView.layoutParams = lp
    }

    override fun getItemCount(): Int {
        return items!!.size
    }
}
