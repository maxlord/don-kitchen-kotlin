package ru.ls.donkitchen.rest.model.response

import com.google.gson.annotations.SerializedName

/**
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 24.10.16
 */
class FcmRegisterResult {
    /**
     * status : true
     * error :
     */

    @SerializedName("item")
    var item: Item? = null

    class Item {
        @SerializedName("status")
        var status: Boolean = false
        @SerializedName("error")
        var error: String? = null
    }
}
