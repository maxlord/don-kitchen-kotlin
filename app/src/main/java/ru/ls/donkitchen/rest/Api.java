package ru.ls.donkitchen.rest;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import ru.ls.donkitchen.rest.model.response.CategoryListResult;
import ru.ls.donkitchen.rest.model.response.ReceiptDetailResult;
import ru.ls.donkitchen.rest.model.response.ReceiptListResult;
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
}
