package com.sammy.newreadingapp.data.paging

import android.annotation.SuppressLint
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.sammy.newreadingapp.data.datasource.NewsLocalDataSource
import com.sammy.newreadingapp.data.datasource.NewsRemoteDataSource
import com.sammy.newreadingapp.data.mapper.toEntity
import com.sammy.newreadingapp.domain.model.ArticleSummary

@OptIn(ExperimentalPagingApi::class)
class ArticleRemoteMediator(
    private val remote: NewsRemoteDataSource,
    private val local: NewsLocalDataSource
) : RemoteMediator<Int, ArticleSummary>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleSummary>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND ->
                return MediatorResult.Success(endOfPaginationReached = true)

            LoadType.APPEND -> state.pages.size + 1

        }

        return try {
            val response = remote.getTopHeadlines(
                page = page,
                pageSize = state.config.pageSize,
                country = "us"
            )

            if (loadType == LoadType.REFRESH) {
                local.clearAll()
            }

            local.insertAll(
                response.articles.map { it.toEntity() }
            )

            val totalLoaded = state.pages.sumOf { it.data.size }

            MediatorResult.Success(
                endOfPaginationReached = totalLoaded >= response.totalResults
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
