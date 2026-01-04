package com.sammy.newreadingapp.domain.usecase

import androidx.paging.PagingData
import com.sammy.newreadingapp.domain.model.ArticleSummary
import com.sammy.newreadingapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(): Flow<PagingData<ArticleSummary>> {
        return repository.getArticlesPaging()
    }
}