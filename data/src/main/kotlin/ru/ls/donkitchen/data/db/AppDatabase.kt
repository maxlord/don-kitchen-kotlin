package ru.ls.donkitchen.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import ru.ls.donkitchen.data.db.entity.Category

@Database(entities = arrayOf(Category::class), version = 1)
abstract class AppDatabase: RoomDatabase() {

}

const val DATABASE_NAME = "main.db"