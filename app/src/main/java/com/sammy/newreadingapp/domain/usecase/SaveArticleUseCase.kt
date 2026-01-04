package com.sammy.newreadingapp.domain.usecase

import com.sammy.newreadingapp.domain.model.ArticleDetail
import com.sammy.newreadingapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveArticleUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(article: ArticleDetail): Flow<Boolean> =
        repository.getProtobufApiResponse(article)
}