package ru.ls.donkitchen.rest.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 27.11.15
 */
public class ReviewResult {

	/**
	 * id : 6
	 * receiptId : 1
	 * date : {"expression":"NOW()","params":[]}
	 * rating : 3
	 * userName : Максим
	 * comments : Отличный рецепт! Всем рекоммендую!
	 */

	@SerializedName("id")
	public int id;
}
