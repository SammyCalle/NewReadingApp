package com.sammy.newreadingapp.presentation.newsdetail

import com.sammy.newreadingapp.domain.model.ArticleDetail

sealed class ArticleDetailUiState {
    object Loading : ArticleDetailUiState()
    data class Success(val article: ArticleDetail) : ArticleDetailUiState()
    data class Error(val message: String) : ArticleDetailUiState()
}