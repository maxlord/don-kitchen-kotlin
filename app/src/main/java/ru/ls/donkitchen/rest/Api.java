package ru.ls.donkitchen.rest;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import ru.ls.donkitchen.rest.model.request.ReceiptIncrementViews;
import ru.ls.donkitchen.rest.model.response.CategoryListResult;
import ru.ls.donkitchen.rest.model.response.ReceiptDetailResult;
import ru.ls.donkitchen.rest.model.response.ReceiptListResult;
import ru.ls.donkitchen.rest.model.response.ReviewListResult;
import ru.ls.donkitchen.rest.model.response.ReviewResult;
import rx.Observable;

/**
 * REST API
 *
 * @author Lord (Kuleshov M.V.)
 * @since 26.08.15
 */
public interface Api {
	@GET("/categories")
	Observable<CategoryListResult> getCategories();

	@GET("/receipts/search")
	Observable<ReceiptListResult> getReceipts(@Query("category_id") int categoryId);

	@GET("/receipts/{id}")
	Observable<ReceiptDetailResult> getReceiptDetail(@Path("id") int receiptId);

	@GET("/reviews/receipt/{id}")
	Observable<ReviewListResult> getReviewsByReceipt(@Path("id") int receiptId);

	@POST("/reviews")
	@FormUrlEncoded
	Observable<ReviewResult> addReview(@Field("receipt_id") int receiptId,
	                                   @Field("rating") int rating,
	                                   @Field("user_name") String userName,
	                                   @Field("comments") String comments);

	@POST("/receipts/increment-views/{id}")
	Observable<ReceiptDetailResult> incrementReceiptViews(@Path("id") int receiptId, @Body ReceiptIncrementViews r);
}
