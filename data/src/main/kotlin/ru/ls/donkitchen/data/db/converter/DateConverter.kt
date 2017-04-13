package ru.ls.donkitchen.data.db.converter

import com.j256.ormlite.field.FieldType
import com.j256.ormlite.field.SqlType
import com.j256.ormlite.field.types.LongType
import java.sql.SQLException
import java.util.*

/**
 * Конвертер даты для хранения в БД в виде числа
 *
 * @author Lord (Kuleshov M.V.)
 * @since 16.10.16
 */
class DateConverter: LongType(SqlType.LONG, arrayOf(Date::class.java)) {
    companion object {
        private val singleton = DateConverter()
    }

    fun getSingleton(): DateConverter {
        return singleton
    }

    @Throws(SQLException::class)
    override fun javaToSqlArg(fieldType: FieldType?, javaObject: Any): Any {
        if (javaObject is Date) {
            return javaObject.time
        }

        return javaObject
    }

    @Throws(SQLException::class)
    override fun sqlArgToJava(fieldType: FieldType?, sqlArg: Any, columnPos: Int): Any? {
        if (sqlArg is Long) {
            return Date(sqlArg)
        }

        return null
    }
}
