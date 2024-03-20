package com.cs4520.assignment5.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cs4520.assignment5.data.models.ProductDto

@Database(entities = [ProductDto::class], version = 1, exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao() : ProductDao
}