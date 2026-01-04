package com.sammy.newreadingapp.data.mapper

import com.sammy.newreadingapp.data.local.entities.NewsDetailEntity
import com.sammy.newreadingapp.data.remote.dto.ArticleDto
import com.sammy.newreadingapp.domain.model.ArticleDetail
import com.sammy.newreadingapp.proto.Article

fun NewsDetailEntity.toDomain(): ArticleDetail =
    ArticleDetail(
        id = (publishedAt + author),
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        title = title,
        urlToImage = urlToImage,
    )

fun ArticleDto.toEntity(): NewsDetailEntity =
    NewsDetailEntity(
        id = (publishedAt + author),
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        title = title,
        urlToImage = urlToImage,
    )

fun ArticleDetail.toProto(): Article =
    Article.newBuilder()
        .setId(id)
        .setTitle(title)
        .setContent(content?:"No Content Found")
        .setAuthor(author?:"No Author Found")
        .setDescription(description?:"No Description Found")
        .setPublishedAt(publishedAt?:"No publishedAt Found")
        .setUrlToImage(urlToImage?:"NO urlToImage Found")
        .build()