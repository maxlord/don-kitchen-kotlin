package ru.ls.donkitchen.rest.model.response

import com.google.gson.annotations.SerializedName

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 16.10.16
 */
class ReviewResult {
    /**
     * id : 6
     * receiptId : 1
     * date : {"expression":"NOW()","params":[]}
     * rating : 3
     * userName : Максим
     * comments : Отличный рецепт! Всем рекоммендую!
     */

    @SerializedName("id")
    var id: Int = 0
}
