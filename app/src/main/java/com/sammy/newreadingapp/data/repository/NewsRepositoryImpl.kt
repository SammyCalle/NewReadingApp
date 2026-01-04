package com.sammy.newreadingapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sammy.newreadingapp.data.datasource.NewsLocalDataSource
import com.sammy.newreadingapp.data.datasource.NewsRemoteDataSource
import com.sammy.newreadingapp.data.mapper.toDomain
import com.sammy.newreadingapp.data.paging.ArticleRemoteMediator
import com.sammy.newreadingapp.domain.model.ArticleDetail
import com.sammy.newreadingapp.domain.model.ArticleSummary
import com.sammy.newreadingapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class NewsRepositoryImpl @Inject constructor(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: NewsLocalDataSource
) : NewsRepository {

    override fun getArticlesPaging(): Flow<PagingData<ArticleSummary>> {
        return Pager(
            config = PagingConfig(
                pageSize = 21,
                initialLoadSize = 21,
                prefetchDistance = 5,
                enablePlaceholders = false,
            ),

            remoteMediator = ArticleRemoteMediator(
                remote = newsRemoteDataSource,
                local = newsLocalDataSource
            ),
            pagingSourceFactory = {
                newsLocalDataSource.getAllNewsSummary()
            }
        ).flow
    }

    override fun getArticleDetail(newId: String): Flow<ArticleDetail> {
        return flow{
            val entity = newsLocalDataSource.getNewById(newId = newId)
            emit(entity.toDomain())
        }

    }

    override fun getProtobufApiResponse(article: ArticleDetail): Flow<Boolean> {
        return flow {
            emit(newsRemoteDataSource.saveArticle(article))
        }

    }

}