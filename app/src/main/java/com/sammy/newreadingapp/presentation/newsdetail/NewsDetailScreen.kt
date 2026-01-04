package com.sammy.newreadingapp.presentation.newsdetail

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.sammy.newreadingapp.domain.model.ArticleDetail


@Composable
fun NewsDetailScreen(
    articleId: String,
    navController: NavHostController,
    viewModel: NewsDetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(articleId) {
        viewModel.loadArticle(articleId)
    }

    LaunchedEffect(key1 = true) {
        viewModel.saveArticleResponse.collect { event ->
            when (event) {
                is UiEvent.ShowMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    when (val state = viewModel.articleDataState) {
        is ArticleDetailUiState.Loading -> CircularProgressIndicator()
        is ArticleDetailUiState.Success -> ArticleContentView(
            state.article,
            viewModel,
            navController = navController
        )

        is ArticleDetailUiState.Error -> Text("Error: ${state.message}")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleContentView(
    article: ArticleDetail,
    viewModel: NewsDetailViewModel,
    navController: NavHostController
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(article.title, style = MaterialTheme.typography.headlineSmall) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }

            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Save") },
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                onClick = { viewModel.onSaveArticle(article) },
            )
        },

        ) { paddingValues ->
        if (isLandscape) {
            ArticleContentLandScapeView(paddingValues = paddingValues, article = article)
        } else {
            ArticleContentPortraitView(paddingValues = paddingValues, article = article)
        }
    }
}

@Composable
fun ArticleContentLandScapeView(paddingValues: PaddingValues, article: ArticleDetail) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        AsyncImage(
            model = article.urlToImage,
            contentDescription = article.description,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            ArticleTextContent(article)
        }
    }
}

@Composable
fun ArticleContentPortraitView(paddingValues: PaddingValues, article: ArticleDetail) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        AsyncImage(
            model = article.urlToImage,
            contentDescription = article.description,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(16.dp))
        )
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ArticleTextContent(article)
        }
    }
}

@Composable
fun ArticleTextContent(article: ArticleDetail) {
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = article.publishedAt ?: "No published time found",
        style = MaterialTheme.typography.labelSmall
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = article.content ?: "No content available",
        style = MaterialTheme.typography.bodyMedium
    )
}