package com.sammy.newreadingapp.data.datasource

import com.sammy.newreadingapp.data.mapper.toProto
import com.sammy.newreadingapp.data.remote.api.ArticleApi
import com.sammy.newreadingapp.data.remote.api.NewsApiService
import com.sammy.newreadingapp.data.remote.dto.TopHeadlinesDto
import com.sammy.newreadingapp.domain.model.ArticleDetail
import com.sammy.newreadingapp.proto.Article
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class NewsRemoteDataSource @Inject constructor(
    private val newsApi: NewsApiService,
    private val articleProtoApi: ArticleApi
) {
    suspend fun getTopHeadlines(
        page: Int,
        pageSize: Int,
        country: String
    ): TopHeadlinesDto {
        return newsApi.getTopHeadlines(
            country = country,
            page = page,
            pageSize = pageSize
        )
    }

    suspend fun saveArticle(article: ArticleDetail): Boolean =
        suspendCancellableCoroutine { cont ->
            articleProtoApi.saveArticle(
                article.toProto(),
                onResult = { response ->
                    cont.resume(response)
                }
            )
        }
}
