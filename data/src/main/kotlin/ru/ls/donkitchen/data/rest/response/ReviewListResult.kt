package ru.ls.donkitchen.data.rest.response

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 16.10.16
 */
class ReviewListResult {
    /**
     * id : 5
     * receiptId : 1
     * date : 2015-11-27 13:57:59
     * rating : 3
     * userName : Максим
     * comments : Отличный рецепт! Всем рекоммендую!
     */

    @SerializedName("reviews")
    var reviews: List<ReviewItem>? = null

    class ReviewItem {
        @SerializedName("id")
        var id: Int = 0
        @SerializedName("receiptId")
        var receiptId: Int = 0
        @SerializedName("date")
        var date: Date? = null
        @SerializedName("rating")
        var rating: Int = 0
        @SerializedName("userName")
        var userName: String? = null
        @SerializedName("comments")
        var comments: String? = null
    }
}