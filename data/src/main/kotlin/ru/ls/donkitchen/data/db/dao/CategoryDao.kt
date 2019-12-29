package ru.ls.donkitchen.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import ru.ls.donkitchen.core.data.entity.CategoryEntity

@Dao
interface CategoryDao {

    @Query("SELECT * FROM categorey")
    fun loadCategories(): List<CategoryEntity>

}