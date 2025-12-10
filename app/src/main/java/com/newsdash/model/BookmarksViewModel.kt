package com.newsdash.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsdash.data.NewsRepository
import com.newsdash.data.local.NewsArticleEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel
@Inject constructor(repository: NewsRepository) : ViewModel() {

    val bookmarks: StateFlow<List<NewsArticleEntity>> =
        repository.getBookmarks()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}