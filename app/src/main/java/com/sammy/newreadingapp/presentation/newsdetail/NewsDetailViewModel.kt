package com.sammy.newreadingapp.presentation.newsdetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sammy.newreadingapp.domain.model.ArticleDetail
import com.sammy.newreadingapp.domain.usecase.GetArticleDetailUseCase
import com.sammy.newreadingapp.domain.usecase.SaveArticleUseCase
import com.sammy.newreadingapp.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val getArticleDetailUseCase: GetArticleDetailUseCase,
    private val saveArticleUseCase: SaveArticleUseCase
) : BaseViewModel() {

    var articleDataState by mutableStateOf<ArticleDetailUiState>(ArticleDetailUiState.Loading)
        private set

    private val _saveArticleResponse = MutableSharedFlow<UiEvent>()
    val saveArticleResponse = _saveArticleResponse.asSharedFlow()

    fun loadArticle(articleId: String) {
        articleDataState = ArticleDetailUiState.Loading
        launchSafe {
            val article = getArticleDetailUseCase(articleId).first()
            articleDataState = ArticleDetailUiState.Success(article)
        }
    }

    fun onSaveArticle(article: ArticleDetail) {
        launchSafe {
            try {
                val response = saveArticleUseCase(article).first()
                if (response) {
                    _saveArticleResponse.emit(UiEvent.ShowMessage("Article saved successfully!"))
                } else {
                    _saveArticleResponse.emit(UiEvent.ShowMessage("Failed to save article"))
                }
            } catch (e: Exception) {
                _saveArticleResponse.emit(UiEvent.ShowMessage("Error: ${e.message}"))
            }
        }
    }
}