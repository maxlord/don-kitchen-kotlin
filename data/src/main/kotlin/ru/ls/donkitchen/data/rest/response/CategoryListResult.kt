package ru.ls.donkitchen.data.rest.response

import com.google.gson.annotations.SerializedName

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 16.10.16
 */
class CategoryListResult {
    @SerializedName("_meta")
    var meta: Meta? = null
    @SerializedName("categories")
    var categories: List<CategoryItem>? = null

    class Meta {
        /**
         * totalCount : 5
         * pageCount : 1
         * currentPage : 1
         * perPage : 20
         */

        @SerializedName("totalCount")
        var totalCount: Int = 0
        @SerializedName("pageCount")
        var pageCount: Int = 0
        @SerializedName("currentPage")
        var currentPage: Int = 0
        @SerializedName("perPage")
        var perPage: Int = 0
    }

    class CategoryItem {
        /**
         * receiptCount : 15
         * id : 1
         * name : Первые блюда
         * priority : 1
         */

        @SerializedName("receiptCount")
        var receiptCount: Int = 0
        @SerializedName("id")
        var id: Int = 0
        @SerializedName("name")
        var name: String? = null
        @SerializedName("priority")
        var priority: Int = 0
        @SerializedName("imageLink")
        var imageLink: String? = null
    }
}