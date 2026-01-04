package com.sammy.newreadingapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sammy.newreadingapp.presentation.news.NewsScreen
import com.sammy.newreadingapp.presentation.newsdetail.NewsDetailScreen

@Composable
fun AppNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.ArticleSummary.route
    ){
        composable(Screen.ArticleSummary.route){
            NewsScreen(navController)
        }

        composable(
            route = Screen.ArticleDetail.route,
            arguments = listOf(
                navArgument("articleId") { type = NavType.StringType }
            )
        ){ backStackEntry ->
            val articleId =
                backStackEntry.arguments?.getString("articleId")!!
            NewsDetailScreen(articleId,navController)
        }
    }
}