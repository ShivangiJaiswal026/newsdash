package com.newsdash.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.newsdash.R
import com.newsdash.data.local.NewsArticleEntity
import com.newsdash.model.NewsFeedViewModel
import com.newsdash.util.ApiResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserFeedScreen(
    onArticleClick: (String) -> Unit,
    onBookmarksClick: () -> Unit
) {
    val viewModel: NewsFeedViewModel = hiltViewModel()
    val state by viewModel.articles.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.today_s_headlines))
                    Button(onClick = onBookmarksClick) { Text(stringResource(R.string.bookmarks)) }
                }
            })
        },
        contentWindowInsets = WindowInsets.safeDrawing
    ) { paddingValues ->
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        when (state) {
            ApiResponse.Loading -> Text(stringResource(R.string.loading))
            is ApiResponse.Error -> Text("Error: ${(state as ApiResponse.Error).message}")
            is ApiResponse.Success -> {
                val articles = (state as ApiResponse.Success).data
                if (articles.isEmpty()) {
                    // Zero state for empty articles
                    ZeroArticlesState()
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(articles.size) { i ->
                            val article = articles[i]
                            NewsTile(article, onArticleClick, viewModel)
                        }
                    }
                }
            }
        }
    }
}
}

@Composable
private fun ZeroArticlesState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.no_articles),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
private fun NewsTile(
    article: NewsArticleEntity,
    onArticleClick: (String) -> Unit,
    viewModel: NewsFeedViewModel
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onArticleClick(article.url) },
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color(0xFFdbd5e8)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                article.title
            )
            Button(
                onClick = {
                    if (!article.isBookmarked)
                        viewModel.bookmarkArticle(article)
                    else
                        viewModel.unbookmarkArticle(article.url)
                },
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Text(
                    if (article.isBookmarked) stringResource(R.string.unbookmark) else stringResource(
                        R.string.bookmark
                    )
                )
            }
        }
    }
}
