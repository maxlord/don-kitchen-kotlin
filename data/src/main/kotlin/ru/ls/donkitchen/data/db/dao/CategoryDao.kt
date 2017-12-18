package ru.ls.donkitchen.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import ru.ls.donkitchen.core.data.entity.CategoryEntity

@Dao
interface CategoryDao {

    @Query("SELECT * FROM categorey")
    fun loadCategories(): List<CategoryEntity>

}