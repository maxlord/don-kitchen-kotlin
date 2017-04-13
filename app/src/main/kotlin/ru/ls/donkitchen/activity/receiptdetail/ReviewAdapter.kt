package ru.ls.donkitchen.activity.receiptdetail

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_review.view.*
import ru.ls.donkitchen.R
import ru.ls.donkitchen.data.rest.response.ReviewListResult
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 17.10.16
 */
class ReviewAdapter(private val context: Context) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {

        }
    }

    private val dateFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    private val items: MutableList<ReviewListResult.ReviewItem>?

    init {
        this.items = ArrayList<ReviewListResult.ReviewItem>()
    }

    fun clear() {
        this.items!!.clear()
    }

    fun addAllItems(items: List<ReviewListResult.ReviewItem>) {
        this.items!!.addAll(items)
    }

    fun getItem(position: Int): ReviewListResult.ReviewItem? {
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
        holder.itemView.comments.text = item.comments
        if (TextUtils.isEmpty(item.comments)) {
            holder.itemView.comments.visibility = View.GONE
        } else {
            holder.itemView.comments.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return items!!.size
    }
}
