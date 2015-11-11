package ru.ls.donkitchen.rest.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 12.10.15
 */
public class CategoryListResult {

	@SerializedName("_meta")
	public Meta meta;
	@SerializedName("categories")
	public List<CategoryItem> categories;

	public static class Meta {
		/**
		 * totalCount : 5
		 * pageCount : 1
		 * currentPage : 1
		 * perPage : 20
		 */

		@SerializedName("totalCount")
		public int totalCount;
		@SerializedName("pageCount")
		public int pageCount;
		@SerializedName("currentPage")
		public int currentPage;
		@SerializedName("perPage")
		public int perPage;
	}

	public static class CategoryItem {
		/**
		 * receiptCount : 15
		 * id : 1
		 * name : Первые блюда
		 * priority : 1
		 */

		@SerializedName("receiptCount")
		public int receiptCount;
		@SerializedName("id")
		public int id;
		@SerializedName("name")
		public String name;
		@SerializedName("priority")
		public int priority;
		@SerializedName("imageLink")
		public String imageLink;
	}
}
