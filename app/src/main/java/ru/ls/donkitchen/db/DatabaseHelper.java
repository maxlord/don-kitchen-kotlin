package ru.ls.donkitchen.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

import ru.ls.donkitchen.BuildConfig;


/**
 * Помощник для работы с БД
 *
 * @author Lord (Kuleshov M.V.)
 * @since 02.06.2015
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	public static final String DATABASE_NAME = "db.sqlite";
	public static final int DATABASE_VERSION = BuildConfig.DATABASE_VERSION;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

	}
}
