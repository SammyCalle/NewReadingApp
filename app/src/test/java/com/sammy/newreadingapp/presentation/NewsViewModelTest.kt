package com.sammy.newreadingapp.presentation

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import app.cash.turbine.test
import com.sammy.newreadingapp.domain.model.ArticleSummary
import com.sammy.newreadingapp.domain.usecase.GetArticlesUseCase
import com.sammy.newreadingapp.presentation.news.NewsViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
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
class NewsViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var getArticlesUseCase: GetArticlesUseCase
    private lateinit var viewModel: NewsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getArticlesUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `articles flow emits PagingData from use case`() = runTest {

        val fakePagingData = PagingData.from(
            listOf(
                ArticleSummary("1", "Title 1"),
                ArticleSummary("2", "Title 2"),
                ArticleSummary("3", "Title 3"),
                ArticleSummary("4", "Title 4")
            )
        )
        coEvery { getArticlesUseCase() } returns flowOf(fakePagingData)

        viewModel = NewsViewModel(getArticlesUseCase)

        viewModel.articles.test {
            val emission = awaitItem()

            val differ = AsyncPagingDataDiffer(
                diffCallback = ArticleSummaryDiffCallback(),
                updateCallback = NoopListCallback(),
                mainDispatcher = testDispatcher,
                workerDispatcher = testDispatcher
            )
            differ.submitData(emission)
            advanceUntilIdle()

            // Assert contents
            val items = differ.snapshot()
            assertEquals(4, items.size)
            assertEquals("Title 1", items[0]?.title)
            assertEquals("Title 2", items[1]?.title)
            assertEquals("Title 3", items[2]?.title)
            assertEquals("Title 4", items[3]?.title)

            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { getArticlesUseCase() }
    }

    class ArticleSummaryDiffCallback : DiffUtil.ItemCallback<ArticleSummary>() {
        override fun areItemsTheSame(oldItem: ArticleSummary, newItem: ArticleSummary) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ArticleSummary, newItem: ArticleSummary) =
            oldItem == newItem
    }

    class NoopListCallback : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
}