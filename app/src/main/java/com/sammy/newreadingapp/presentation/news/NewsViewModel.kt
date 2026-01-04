package com.sammy.newreadingapp.presentation.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sammy.newreadingapp.data.remote.api.ArticleApi
import com.sammy.newreadingapp.domain.model.ArticleSummary
import com.sammy.newreadingapp.domain.usecase.GetArticlesUseCase
import com.sammy.newreadingapp.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    getArticlesUseCase: GetArticlesUseCase,
) : BaseViewModel() {

    val articles: Flow<PagingData<ArticleSummary>> =
        getArticlesUseCase()
            .cachedIn(viewModelScope)
}