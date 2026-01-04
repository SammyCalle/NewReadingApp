package com.sammy.newreadingapp.data.datasource

import androidx.paging.PagingSource
import com.sammy.newreadingapp.data.local.dao.NewsDao
import com.sammy.newreadingapp.data.local.entities.NewsDetailEntity
import com.sammy.newreadingapp.domain.model.ArticleSummary
import javax.inject.Inject

class NewsLocalDataSource @Inject constructor(
    private val newsDao: NewsDao
) {

    suspend fun getNewById(newId: String): NewsDetailEntity =
        newsDao.getNewById(newId = newId)

    fun getAllNewsSummary(): PagingSource<Int, ArticleSummary> =
        newsDao.getAllNewsSummary()

    suspend fun insertAll(news: List<NewsDetailEntity>) =
        newsDao.insertAll(news = news)

    suspend fun clearAll() = newsDao.clearAll()
}