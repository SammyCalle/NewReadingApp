package com.sammy.newreadingapp.di

import android.content.Context
import androidx.room.Room
import com.sammy.newreadingapp.data.datasource.NewsLocalDataSource
import com.sammy.newreadingapp.data.remote.api.NewsApiService
import com.sammy.newreadingapp.data.remote.api.RetrofitConfiguration
import com.sammy.newreadingapp.data.datasource.NewsRemoteDataSource
import com.sammy.newreadingapp.data.local.dao.NewsDao
import com.sammy.newreadingapp.data.local.database.AppDatabase
import com.sammy.newreadingapp.data.remote.api.ArticleApi
import com.sammy.newreadingapp.data.repository.NewsRepositoryImpl
import com.sammy.newreadingapp.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return RetrofitConfiguration.newsApi
    }

    @Provides
    @Singleton
    fun provideNewsApiService(newsApi: Retrofit): NewsApiService {
        return newsApi.create(NewsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRemoteDataSource(
        newsApi: NewsApiService,
        articleProtoApi: ArticleApi
    ): NewsRemoteDataSource {
        return NewsRemoteDataSource(newsApi = newsApi, articleProtoApi = articleProtoApi)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsRemoteDataSource: NewsRemoteDataSource,
        newsLocalDataSource: NewsLocalDataSource
    ): NewsRepository {
        return NewsRepositoryImpl(
            newsRemoteDataSource = newsRemoteDataSource,
            newsLocalDataSource = newsLocalDataSource
        )
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "news_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideArticleDao(
        database: AppDatabase
    ): NewsDao {
        return database.newsDao()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().build()

    @Provides
    @Singleton
    @BaseUrl
    fun provideBaseUrl(): HttpUrl =
        "https://example.com/".toHttpUrl()

    @Provides
    @Singleton
    fun provideArticleApi(
        client: OkHttpClient,
        @BaseUrl baseUrl: HttpUrl
    ): ArticleApi = ArticleApi(client, baseUrl)

}