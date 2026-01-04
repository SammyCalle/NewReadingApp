package com.sammy.newreadingapp.domain.usecase

import com.sammy.newreadingapp.domain.model.ArticleDetail
import com.sammy.newreadingapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArticleDetailUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(id: String): Flow<ArticleDetail> {
        return repository.getArticleDetail(newId = id)
    }
}