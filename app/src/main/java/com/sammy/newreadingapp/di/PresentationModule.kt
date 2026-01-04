package com.sammy.newreadingapp.di

import androidx.lifecycle.ViewModel
import com.sammy.newreadingapp.domain.usecase.GetArticleDetailUseCase
import com.sammy.newreadingapp.domain.usecase.GetArticlesUseCase
import com.sammy.newreadingapp.domain.usecase.SaveArticleUseCase
import com.sammy.newreadingapp.presentation.news.NewsViewModel
import com.sammy.newreadingapp.presentation.newsdetail.NewsDetailViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PresentationModule {

    @Provides
    @Singleton
    fun provideNewsViewModel(getArticlesUseCase: GetArticlesUseCase): ViewModel {
        return NewsViewModel(getArticlesUseCase = getArticlesUseCase)
    }

    @Provides
    @Singleton
    fun providesNewDetailViewModel(
        getArticleDetailUseCase: GetArticleDetailUseCase,
        saveArticleUseCase: SaveArticleUseCase
    ): ViewModel {
        return NewsDetailViewModel(
            getArticleDetailUseCase = getArticleDetailUseCase,
            saveArticleUseCase = saveArticleUseCase
        )
    }
}