package ru.ls.donkitchen.data.rest

import retrofit2.http.GET
import ru.ls.donkitchen.data.rest.response.CategoryListResult
import rx.Single

/**
 * АПИ
 *
 * @author Lord (Kuleshov M.V.)
 * @since 11.01.16
 */
interface Api {
    @GET("categories")
    fun getCategories(): Single<CategoryListResult>

//    @GET("receipts/search")
//    fun getReceipts(@Query("category_id") categoryId: Int): Observable<ReceiptListResult>
//
//    @GET("receipts/{id}")
//    fun getReceiptDetail(@Path("id") receiptId: Int): Observable<ReceiptDetailResult>
//
//    @GET("reviews/receipt/{id}")
//    fun getReviewsByReceipt(@Path("id") receiptId: Int): Observable<ReviewListResult>
//
//    @POST("reviews")
//    @FormUrlEncoded
//    fun addReview(@Field("receipt_id") receiptId: Int,
//                                  @Field("rating") rating: Int,
//                                  @Field("user_name") userName: String,
//                                  @Field("comments") comments: String): Observable<ReviewResult>
//
//    @POST("receipts/increment-views/{id}")
//    fun incrementReceiptViews(@Path("id") receiptId: Int, @Body r: ReceiptIncrementViews): Observable<ReceiptDetailResult>
//
//    @POST("fcm/register")
//    @FormUrlEncoded
//    fun registerFcm(@Field("token") token: String,
//                    @Field("device") deviceName: String,
//                    @Field("app_name") appName: String): Observable<FcmRegisterResult>
}
