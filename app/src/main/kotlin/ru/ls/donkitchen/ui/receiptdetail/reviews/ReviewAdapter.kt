package ru.ls.donkitchen.ui.receiptdetail.reviews

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_review.view.*
import ru.ls.donkitchen.R
import java.text.SimpleDateFormat
import java.util.*

class ReviewAdapter(private val context: Context) : androidx.recyclerview.widget.RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
    class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

    private val dateFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    private val items: MutableList<ReviewViewItem>?

    init {
        this.items = arrayListOf()
    }

    fun clear() {
        this.items!!.clear()
    }

    fun addAllItems(items: List<ReviewViewItem>) {
        this.items!!.addAll(items)
    }

    fun getItem(position: Int): ReviewViewItem? {
        if (items != null && position < items.size) {
            return items[position]
        }

        return null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_review, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items!![position]

        holder.itemView.date.text = dateFormatter.format(item.date)
        holder.itemView.rating.rating = item.rating.toFloat()
        holder.itemView.author.text = item.userName
        if (TextUtils.isEmpty(item.userName)) {
            holder.itemView.author.visibility = View.GONE
        } else {
            holder.itemView.author.visibility = View.VISIBLE
        }
        holder.itemView.comments.text = item.comments?.trim()
        if (item.comments.isNullOrBlank()) {
            holder.itemView.comments.visibility = View.GONE
        } else {
            holder.itemView.comments.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

}