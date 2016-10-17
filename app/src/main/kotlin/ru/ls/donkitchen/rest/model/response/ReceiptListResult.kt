package ru.ls.donkitchen.rest.model.response

import com.google.gson.annotations.SerializedName

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 16.10.16
 */
class ReceiptListResult {
    /**
     * receipts : [{"id":1,"categoryId":2,"name":"Гуляш из говядины","imageLink":"http://dk.lord-services.ru/uploads/956032ef23dd18a30a83763b440c40e9.jpg","ingredients":"пару зубков чеснока","receipt":"зелень.","categoryName":"Вторые блюда"}]
     */

    @SerializedName("receipts")
    var receipts: List<ReceiptItem>? = null

    class ReceiptItem {
        /**
         * id : 1
         * categoryId : 2
         * name : Гуляш из говядины
         * imageLink : http://dk.lord-services.ru/uploads/956032ef23dd18a30a83763b440c40e9.jpg
         * ingredients : пару зубков чеснока
         * receipt : зелень.
         * categoryName : Вторые блюда
         */

        @SerializedName("id")
        var id: Int = 0
        @SerializedName("categoryId")
        var categoryId: Int = 0
        @SerializedName("name")
        var name: String? = null
        @SerializedName("imageLink")
        var imageLink: String? = null
        @SerializedName("ingredients")
        var ingredients: String? = null
        @SerializedName("receipt")
        var receipt: String? = null
        @SerializedName("categoryName")
        var categoryName: String? = null
        @SerializedName("views")
        var views: Int = 0
        @SerializedName("rating")
        var rating: Int = 0
    }
}