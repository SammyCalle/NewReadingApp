package com.sammy.newreadingapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sammy.newreadingapp.data.local.entities.NewsDetailEntity
import com.sammy.newreadingapp.domain.model.ArticleSummary

@Dao
interface NewsDao {

    @Query("SELECT * FROM news WHERE id = :newId")
    suspend fun getNewById(newId: String) : NewsDetailEntity

    @Query("SELECT id, title FROM news ORDER BY published_at DESC")
    fun getAllNewsSummary(): PagingSource<Int,ArticleSummary>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news : List<NewsDetailEntity>)

    @Query("DELETE FROM news")
    suspend fun clearAll()
}