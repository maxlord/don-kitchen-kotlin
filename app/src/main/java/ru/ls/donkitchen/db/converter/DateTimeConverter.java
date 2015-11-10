package ru.ls.donkitchen.db.converter;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.LongType;

import org.joda.time.DateTime;

import java.sql.SQLException;

/**
 * Конвертер JodaTime -> Long и обратно
 * Используется в описании поля для таблицы БД
 *
 * @author Lord (Kuleshov M.V.)
 * @since 21.09.15
 */
public class DateTimeConverter extends LongType {
	private static final DateTimeConverter singleton = new DateTimeConverter();

	protected DateTimeConverter() {
		super(SqlType.LONG, new Class<?>[] { DateTime.class });
	}

	public static DateTimeConverter getSingleton() {
		return singleton;
	}

	@Override
	public Object javaToSqlArg(FieldType fieldType, Object javaObject)
			throws SQLException {
		if (javaObject instanceof DateTime) {
			return ((DateTime) javaObject).getMillis();
		}

		return javaObject;
	}

	@Override
	public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos)
			throws SQLException {
		if (sqlArg instanceof Long) {
			return new DateTime(sqlArg);
		}

		return null;
	}
}
