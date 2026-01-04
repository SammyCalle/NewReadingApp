package com.sammy.newreadingapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsDetailEntity(
    @PrimaryKey val id: String,
    val author: String?,
    val content: String?,
    val description: String?,
    @ColumnInfo(name = "published_at")val publishedAt: String?,
    val title: String,
    @ColumnInfo(name = "url_to_image")val urlToImage: String?
)

