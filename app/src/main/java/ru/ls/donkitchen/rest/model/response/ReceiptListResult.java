package ru.ls.donkitchen.rest.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 13.10.15
 */
public class ReceiptListResult {

	/**
	 * receipts : [{"id":1,"categoryId":2,"name":"Гуляш из говядины","imageLink":"http://dk.lord-services.ru/uploads/956032ef23dd18a30a83763b440c40e9.jpg","ingredients":"пару зубков чеснока","receipt":"зелень.","categoryName":"Вторые блюда"}]
	 */

	@SerializedName("receipts")
	public List<ReceiptItem> receipts;

	public static class ReceiptItem {
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
		public int id;
		@SerializedName("categoryId")
		public int categoryId;
		@SerializedName("name")
		public String name;
		@SerializedName("imageLink")
		public String imageLink;
		@SerializedName("ingredients")
		public String ingredients;
		@SerializedName("receipt")
		public String receipt;
		@SerializedName("categoryName")
		public String categoryName;
	}
}
