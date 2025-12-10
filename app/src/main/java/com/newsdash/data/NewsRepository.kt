package com.newsdash.data

import com.newsdash.data.local.AppDatabase
import com.newsdash.data.local.NewsArticleEntity
import com.newsdash.data.remote.NewsService
import com.newsdash.util.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
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
            val newArticles = response.articles.map { it.toEntity() }
            //We need to preserves bookmark status when refreshing articles hence before clearing,
            // map existing articles and their bookmark status to new articles before inserting
            val existingArticles = db.newsArticleDao().getAllArticles().first()
            val bookmarkStatusMap = existingArticles.associateBy { it.url }

            //Ensuring bookmarked articles remain bookmarked after refresh
            val articlesToInsert = newArticles.map { newArticle ->
                val existing = bookmarkStatusMap[newArticle.url]
                if (existing != null && existing.isBookmarked) {
                    newArticle.copy(isBookmarked = true)
                } else {
                    newArticle
                }
            }
            
            db.newsArticleDao().clearAll()
            db.newsArticleDao().insertAll(articlesToInsert)
            
            val articlesWithBookmarks = db.newsArticleDao().getAllArticles().first()
            emit(ApiResponse.Success(articlesWithBookmarks))
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

    // Get all articles
    fun getAllArticles(): Flow<List<NewsArticleEntity>> = db.newsArticleDao().getAllArticles()

}
