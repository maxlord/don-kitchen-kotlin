package ru.ls.donkitchen.data.rest

import io.reactivex.Single
import retrofit2.http.*
import ru.ls.donkitchen.data.rest.request.ReceiptIncrementViews
import ru.ls.donkitchen.data.rest.response.*

/**
 * АПИ
 *
 * @author Lord (Kuleshov M.V.)
 * @since 11.01.16
 */
interface Api {
    @GET("categories")
    fun getCategories(): Single<CategoryListResult>

    @GET("receipts/search")
    fun getReceipts(@Query("category_id") categoryId: Int): Single<ReceiptListResult>

    @GET("receipts/{id}")
    fun getReceiptDetail(@Path("id") receiptId: Int): Single<ReceiptDetailResult>

    @GET("reviews/receipt/{id}")
    fun getReviewsByReceipt(@Path("id") receiptId: Int): Single<ReviewListResult>

    @POST("reviews")
    @FormUrlEncoded
    fun addReview(@Field("receipt_id") receiptId: Int,
                  @Field("rating") rating: Int,
                  @Field("user_name") userName: String,
                  @Field("comments") comments: String): Single<ReviewResult>

    @POST("receipts/increment-views/{id}")
    fun incrementReceiptViews(@Path("id") receiptId: Int, @Body r: ReceiptIncrementViews): Single<Unit>

    @POST("fcm/register")
    @FormUrlEncoded
    fun registerFcm(@Field("token") token: String,
                    @Field("device") deviceName: String,
                    @Field("app_name") appName: String): Single<FcmRegisterResult>
}
