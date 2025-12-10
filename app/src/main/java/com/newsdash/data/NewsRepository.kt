package com.newsdash.data

import com.newsdash.data.local.AppDatabase
import com.newsdash.data.local.NewsArticleEntity
import com.newsdash.data.remote.NewsService
import com.newsdash.util.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository
@Inject
constructor(private val newsService: NewsService, private val db: AppDatabase) {

    // Fetch articles from API and store locally
    fun getHeadlineArticles(): Flow<ApiResponse<List<NewsArticleEntity>>> = flow {
        emit(ApiResponse.Loading)
        try {
            val response = newsService.getHeadlineArticles()
            val articles = response.articles.map { it.toEntity() }
            db.newsArticleDao().clearAll()
            db.newsArticleDao().insertAll(articles)
            emit(ApiResponse.Success(articles))
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.localizedMessage))
        }
    }

    // Bookmark an article
    suspend fun bookmarkArticle(article: NewsArticleEntity) =
        db.newsArticleDao().upsert(article.copy(isBookmarked = true))

    // Remove bookmark
    suspend fun unbookmarkArticle(url: String) =
        db.newsArticleDao().setBookmark(url, false)

    // Get bookmarks from DB
    fun getBookmarks(): Flow<List<NewsArticleEntity>> = db.newsArticleDao().getBookmarks()

}
