package ru.ls.donkitchen.data.rest.response

import com.google.gson.annotations.SerializedName

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 16.10.16
 */
class ReceiptDetailResult {
    /**
     * id : 2
     * categoryId : 1
     * name : Уха по-старочеркасски
     * imageLink : http://dk.lord-services.ru/uploads/e63cee0eff2abaa90ac36ce93defae1a.png
     * ingredients :
     * receipt : Мелкая рыба
     * categoryName : Первые блюда
     */

    @SerializedName("id")
    var id: Int = 0
    @SerializedName("categoryId")
    var categoryId: Int = 0
    @SerializedName("name")
    var name: String = ""
    @SerializedName("imageLink")
    var imageLink: String? = null
    @SerializedName("ingredients")
    var ingredients: String? = null
    @SerializedName("receipt")
    var receipt: String = ""
    @SerializedName("categoryName")
    var categoryName: String? = null
    @SerializedName("views")
    var views: Int = 0
    @SerializedName("rating")
    var rating: Int = 0
}