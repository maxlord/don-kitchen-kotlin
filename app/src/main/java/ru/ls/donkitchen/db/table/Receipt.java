package ru.ls.donkitchen.db.table;

import android.provider.BaseColumns;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 11.11.15
 */
@DatabaseTable(tableName = "receipt")
public class Receipt implements BaseColumns {
	public static final String NAME = "name";
	public static final String INGREDIENTS = "ingredients";
	public static final String CATEGORY_ID = "category_id";
	public static final String RECEIPT = "receipt";
	public static final String IMAGE_LINK = "image_link";
	public static final String VIEWS_COUNT = "views_count";

	@DatabaseField(id = true, canBeNull = false, columnName = _ID)
	public int id;
	@DatabaseField(canBeNull = false, foreign = true, columnName = CATEGORY_ID, foreignColumnName = _ID, foreignAutoRefresh = true)
	public Category category;
	@DatabaseField(canBeNull = false, columnName = NAME)
	public String name;
	@DatabaseField(canBeNull = false, columnName = IMAGE_LINK)
	public String imageLink;
	@DatabaseField(columnName = INGREDIENTS)
	public String ingredients;
	@DatabaseField(canBeNull = false, columnName = RECEIPT)
	public String receipt;
	@DatabaseField(columnName = VIEWS_COUNT)
	public int viewsCount;

	@Override
	public String toString() {
		return "Receipt{" +
				"id=" + id +
				", category=" + category +
				", name='" + name + '\'' +
				", imageLink='" + imageLink + '\'' +
				", ingredients='" + ingredients + '\'' +
				", receipt='" + receipt + '\'' +
				", viewsCount=" + viewsCount +
				'}';
	}
}
