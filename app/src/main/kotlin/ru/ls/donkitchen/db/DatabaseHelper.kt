package ru.ls.donkitchen.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import ru.ls.donkitchen.BuildConfig

/**
 * Помошник для работы с БД
 *
 * @author Lord (Kuleshov M.V.)
 * @since 16.10.16
 */
class DatabaseHelper(context: Context): OrmLiteSqliteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        val DATABASE_NAME = "db.sqlite"
        val DATABASE_VERSION = BuildConfig.DATABASE_VERSION
    }

    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {

    }

    override fun onUpgrade(database: SQLiteDatabase?, connectionSource: ConnectionSource?, oldVersion: Int, newVersion: Int) {

    }
}
