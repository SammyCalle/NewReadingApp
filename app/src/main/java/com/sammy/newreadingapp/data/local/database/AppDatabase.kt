package com.sammy.newreadingapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sammy.newreadingapp.data.local.dao.NewsDao
import com.sammy.newreadingapp.data.local.entities.NewsDetailEntity

@Database(entities = [NewsDetailEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}