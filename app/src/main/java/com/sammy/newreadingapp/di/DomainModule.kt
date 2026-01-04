package com.sammy.newreadingapp.di

import androidx.lifecycle.ViewModel
import com.sammy.newreadingapp.domain.repository.NewsRepository
import com.sammy.newreadingapp.domain.usecase.GetArticleDetailUseCase
import com.sammy.newreadingapp.domain.usecase.GetArticlesUseCase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideGetArticlesUseCase(repository: NewsRepository) : GetArticlesUseCase{
        return GetArticlesUseCase(repository = repository)
    }

    @Provides
    @Singleton
    fun provideGetArticleDetailsUseCase(repository: NewsRepository) : GetArticleDetailUseCase{
        return GetArticleDetailUseCase(repository = repository)
    }
}