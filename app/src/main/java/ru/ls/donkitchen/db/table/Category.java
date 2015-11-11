package ru.ls.donkitchen.db.table;

import android.provider.BaseColumns;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 11.11.15
 */
@DatabaseTable(tableName = "category")
public class Category implements BaseColumns {
	public static final String NAME = "name";
	public static final String IMAGE_LINK = "image_link";
	public static final String RECEIPT_COUNT = "receipt_count";
	public static final String PRIORITY = "priority";

	@DatabaseField(id = true, canBeNull = false, columnName = _ID)
	public int id;
	@DatabaseField(canBeNull = false, columnName = NAME)
	public String name;
	@DatabaseField(canBeNull = false, columnName = IMAGE_LINK)
	public String imageLink;
	@DatabaseField(columnName = RECEIPT_COUNT)
	public int receiptCount;
	@DatabaseField(columnName = PRIORITY)
	public int priority;

	@Override
	public String toString() {
		return "Category{" +
				"id=" + id +
				", name='" + name + '\'' +
				", imageLink='" + imageLink + '\'' +
				", receiptCount=" + receiptCount +
				", priority=" + priority +
				'}';
	}
}
