package com.sammy.newreadingapp.data.remote.api

import com.sammy.newreadingapp.data.remote.dto.TopHeadlinesDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsApiService {

    @Headers(
        "X-Api-Key:a4755312dcba406eabf473b54fba550b"
    )
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize : Int): TopHeadlinesDto
}