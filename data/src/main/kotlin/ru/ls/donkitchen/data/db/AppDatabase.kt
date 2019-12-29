package ru.ls.donkitchen.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.ls.donkitchen.data.db.entity.Category

@Database(entities = arrayOf(Category::class), version = 1)
abstract class AppDatabase: RoomDatabase() {

}

const val DATABASE_NAME = "main.db"