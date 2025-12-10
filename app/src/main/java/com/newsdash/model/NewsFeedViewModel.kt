package com.newsdash.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsdash.data.NewsRepository
import com.newsdash.data.local.NewsArticleEntity
import com.newsdash.util.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {
    private val _articles =
        MutableStateFlow<ApiResponse<List<NewsArticleEntity>>>(ApiResponse.Loading)
    val articles: StateFlow<ApiResponse<List<NewsArticleEntity>>> = _articles.asStateFlow()

    init {
        fetchHeadlines()
    }

    fun fetchHeadlines() {
        viewModelScope.launch {
            repository.getHeadlineArticles().collect { _articles.value = it }
        }
    }

    fun bookmarkArticle(article: NewsArticleEntity) = viewModelScope.launch {
        repository.bookmarkArticle(article)
    }

    fun unbookmarkArticle(url: String) = viewModelScope.launch {
        repository.unbookmarkArticle(url)
    }
}