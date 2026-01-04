package com.sammy.newreadingapp.data.remote.api

import com.sammy.newreadingapp.proto.Article
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException
import javax.inject.Inject

class ArticleApi @Inject constructor(
    private val client: OkHttpClient,
    private val baseUrl: HttpUrl
) {
    fun saveArticle(
        article: Article,
        onResult: (Boolean) -> Unit
    ) {
        val request = Request.Builder()
            .url(baseUrl.newBuilder().addPathSegment("articles").build())
            .post(
                article.toByteArray()
                    .toRequestBody("application/x-protobuf".toMediaType())
            )
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                onResult(false)
            }

            override fun onResponse(call: Call, response: Response) {
                //Removed because no Protobuff server to response, so it will simulate a positive
                // response
//                onResult(response.isSuccessful)
                onResult(true)
            }
        })
    }
}
