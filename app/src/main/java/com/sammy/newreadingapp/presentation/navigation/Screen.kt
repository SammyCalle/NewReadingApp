package com.sammy.newreadingapp.presentation.navigation

sealed class Screen(val route: String) {
    object ArticleSummary : Screen("articles")

    object ArticleDetail : Screen("article_detail/{articleId}"){
        fun createRoute(articleId: String) =
            "article_detail/$articleId"
    }
}