package com.sammy.newreadingapp.presentation

import app.cash.turbine.test
import com.sammy.newreadingapp.data.remote.api.ArticleApi
import com.sammy.newreadingapp.domain.model.ArticleDetail
import com.sammy.newreadingapp.domain.usecase.GetArticleDetailUseCase
import com.sammy.newreadingapp.domain.usecase.SaveArticleUseCase
import com.sammy.newreadingapp.presentation.newsdetail.ArticleDetailUiState
import com.sammy.newreadingapp.presentation.newsdetail.NewsDetailViewModel
import com.sammy.newreadingapp.presentation.newsdetail.UiEvent
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class NewsDetailViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var getArticleDetailUseCase: GetArticleDetailUseCase
    private lateinit var saveArticleUseCase: SaveArticleUseCase
    private lateinit var viewModel: NewsDetailViewModel

    @Before
    fun setup(){
        Dispatchers.setMain(testDispatcher)
        getArticleDetailUseCase = mockk()
        saveArticleUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `use case returns a flow from use case`() = runTest {
        val articleId = "@214das"
        val fakeArticleDetail =
            ArticleDetail(
                id = "@214das",
                author = "author",
                content = "content",
                description = "description",
                publishedAt = "publishedAt",
                title = "title",
                urlToImage = "urlToImage"
            )


        every { getArticleDetailUseCase.invoke(articleId) } returns flowOf(fakeArticleDetail)

        viewModel = NewsDetailViewModel(getArticleDetailUseCase,saveArticleUseCase)

        viewModel.loadArticle(articleId)
        advanceUntilIdle()

        assert(viewModel.articleDataState is ArticleDetailUiState.Success)
        assert(
            (viewModel.articleDataState as ArticleDetailUiState.Success).article == fakeArticleDetail
        )
    }

    @Test
    fun `onSaveArticle emits success message`() = runTest {

        val fakeArticleDetail = ArticleDetail(
            id = "@214das",
            author = "author",
            content = "content",
            description = "description",
            publishedAt = "publishedAt",
            title = "title",
            urlToImage = "urlToImage"
        )

        every {
            saveArticleUseCase.invoke(fakeArticleDetail)
        } returns flowOf(true)

        viewModel = NewsDetailViewModel(
            getArticleDetailUseCase = getArticleDetailUseCase,
            saveArticleUseCase = saveArticleUseCase
        )

        viewModel.saveArticleResponse.test {
            viewModel.onSaveArticle(fakeArticleDetail)

            val event = awaitItem()
            assert(event is UiEvent.ShowMessage)
            assert((event as UiEvent.ShowMessage).message == "Article saved successfully!")
            cancelAndIgnoreRemainingEvents()
        }
    }
}