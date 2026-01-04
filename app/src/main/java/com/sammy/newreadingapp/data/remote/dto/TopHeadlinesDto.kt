package com.sammy.newreadingapp.data.remote.dto

data class TopHeadlinesDto(
    val articles: List<ArticleDto>,
    val status: String,
    val totalResults: Int
)