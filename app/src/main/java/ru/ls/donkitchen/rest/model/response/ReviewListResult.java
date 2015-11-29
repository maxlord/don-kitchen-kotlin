package ru.ls.donkitchen.rest.model.response;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.util.List;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 27.11.15
 */
public class ReviewListResult {

	/**
	 * id : 5
	 * receiptId : 1
	 * date : 2015-11-27 13:57:59
	 * rating : 3
	 * userName : Максим
	 * comments : Отличный рецепт! Всем рекоммендую!
	 */

	@SerializedName("reviews")
	public List<ReviewItem> reviews;

	public static class ReviewItem {
		@SerializedName("id")
		public int id;
		@SerializedName("receiptId")
		public int receiptId;
		@SerializedName("date")
		public DateTime date;
		@SerializedName("rating")
		public int rating;
		@SerializedName("userName")
		public String userName;
		@SerializedName("comments")
		public String comments;
	}
}
