package com.newsdash.data.remote

import com.newsdash.data.local.NewsArticleEntity
import com.newsdash.util.AppConstants
import com.newsdash.util.AppConstants.Companion.EN
import com.newsdash.util.AppConstants.Companion.GENERAL
import com.newsdash.util.AppConstants.Companion.IN
import com.squareup.moshi.JsonClass
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsService {
    @GET("top-headlines")
    suspend fun getHeadlineArticles(
        @Query("category") category: String = GENERAL,
        @Query("lang") lang: String = EN,
        @Query("country") country: String = IN,
        @Query("max") max: Int = 10,
        @Query("apikey") apiKey: String = AppConstants.API_KEY
    ): HeadlineApiResponse
}

@JsonClass(generateAdapter = true)
data class HeadlineApiResponse(
    val information: Information?,
    val totalArticles: Int?,
    val articles: List<ArticleDto>
)

@JsonClass(generateAdapter = true)
data class Information(
    val realTimeArticles: RealTimeArticles?
)

@JsonClass(generateAdapter = true)
data class RealTimeArticles(
    val message: String?
)

@JsonClass(generateAdapter = true)
data class ArticleDto(
    val title: String?,
    val description: String?,
    val url: String?,
    val image: String?,
    val publishedAt: String?
) {
    fun toEntity() = NewsArticleEntity(
        url = url ?: "",
        title = title ?: "",
        description = description,
        imageUrl = image,
        publishedAt = publishedAt ?: "",
        isBookmarked = false
    )
}




