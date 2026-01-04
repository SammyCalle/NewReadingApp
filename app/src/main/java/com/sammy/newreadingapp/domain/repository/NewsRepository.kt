package com.sammy.newreadingapp.domain.repository

import androidx.paging.PagingData
import com.sammy.newreadingapp.domain.model.ArticleSummary
import com.sammy.newreadingapp.domain.model.ArticleDetail
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getArticlesPaging(): Flow<PagingData<ArticleSummary>>
    fun getArticleDetail(newId: String): Flow<ArticleDetail>

    fun getProtobufApiResponse(article: ArticleDetail) :  Flow<Boolean>
}