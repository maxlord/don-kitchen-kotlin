package ru.ls.donkitchen.rest.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 13.10.15
 */
public class ReceiptDetailResult {

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
	@SerializedName("views")
	public int views;
	@SerializedName("rating")
	public int rating;
}
