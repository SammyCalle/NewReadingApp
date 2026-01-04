package com.sammy.newreadingapp.domain.model

data class ArticleDetail(
    val id : String,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val title: String,
    val urlToImage: String?
)
