package com.newsdash

import app.cash.turbine.test
import com.newsdash.data.NewsRepository
import com.newsdash.data.local.NewsArticleDao
import com.newsdash.data.local.NewsArticleEntity
import com.newsdash.data.remote.ArticleDto
import com.newsdash.data.remote.HeadlineApiResponse
import com.newsdash.data.remote.NewsService
import com.newsdash.util.ApiResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class NewsRepositoryTest {

    private lateinit var newsService: NewsService
    private lateinit var newsDao: NewsArticleDao
    private lateinit var repository: NewsRepository

    private val sampleArticle = NewsArticleEntity(
        url = "url1", title = "Title1", description = "Desc", imageUrl = null,
        publishedAt = "today", isBookmarked = false
    )

    @Before
    fun setup() {
        newsService = mockk()
        newsDao = mockk()
        val db = mockk<com.newsdash.data.local.AppDatabase> {
            every { newsArticleDao() } returns newsDao
        }
        repository = NewsRepository(newsService, db)
    }

    @Test
    fun `getHeadlineArticles emits loading and success`() = runTest {
        val dto = ArticleDto("Title1", "Desc", "url1", null, "today")
        coEvery { newsService.getHeadlineArticles() } returns HeadlineApiResponse(null, 1, listOf(dto))
        coEvery { newsDao.getAllArticles() } returns flowOf(emptyList())
        coEvery { newsDao.clearAll() } returns Unit
        coEvery { newsDao.insertAll(any()) } returns Unit
        coEvery { newsDao.getAllArticles() } returns flowOf(listOf(sampleArticle))

        repository.getHeadlineArticles().test {
            assertEquals(ApiResponse.Loading, awaitItem())
            val success = awaitItem() as ApiResponse.Success
            assertEquals(1, success.data.size)
            assertEquals("Title1", success.data[0].title)
            awaitComplete()
        }
    }

    @Test
    fun `bookmarkArticle calls DAO upsert with bookmarked true`() = runTest {
        coEvery { newsDao.upsert(any()) } returns Unit

        repository.bookmarkArticle(sampleArticle)

        coVerify { newsDao.upsert(sampleArticle.copy(isBookmarked = true)) }
    }

    @Test
    fun `unbookmarkArticle calls DAO setBookmark with false`() = runTest {
        coEvery { newsDao.setBookmark(any(), any()) } returns Unit

        repository.unbookmarkArticle("url1")

        coVerify { newsDao.setBookmark("url1", false) }
    }

    @Test
    fun `getBookmarks returns DAO bookmarks`() = runTest {
        coEvery { newsDao.getBookmarks() } returns flowOf(listOf(sampleArticle))

        repository.getBookmarks().test {
            val result = awaitItem()
            assertEquals(1, result.size)
            assertEquals("Title1", result[0].title)
            awaitComplete()
        }
    }
}
