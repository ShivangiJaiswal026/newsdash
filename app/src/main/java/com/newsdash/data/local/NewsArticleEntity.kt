package com.newsdash.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class NewsArticleEntity(
    @PrimaryKey val url: String,
    val title: String,
    val description: String?,
    val imageUrl: String?,
    val publishedAt: String,
    val source: String?,
    val isBookmarked: Boolean = false
)