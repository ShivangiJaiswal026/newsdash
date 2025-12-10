package com.newsdash.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.newsdash.R
import com.newsdash.data.local.NewsArticleEntity
import com.newsdash.model.NewsFeedViewModel
import com.newsdash.util.ApiResponse

@Composable
fun UserFeedScreen(
    onArticleClick: (String) -> Unit,
    onBookmarksClick: () -> Unit
) {
    val viewModel: NewsFeedViewModel = hiltViewModel()
    val state by viewModel.articles.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)) {
        Text(stringResource(R.string.today_s_headlines))
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onBookmarksClick, modifier = Modifier.fillMaxWidth()) { Text(stringResource(R.string.bookmarks)) }

        Spacer(modifier = Modifier.height(20.dp))

        when (state) {
            ApiResponse.Loading -> Text(stringResource(R.string.loading))
            is ApiResponse.Error -> Text("Error: ${(state as ApiResponse.Error).message}")
            is ApiResponse.Success -> {
                val articles = (state as ApiResponse.Success).data
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

@Composable
private fun NewsTile(
    article: NewsArticleEntity,
    onArticleClick: (String) -> Unit,
    viewModel: NewsFeedViewModel
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color(0xFFdbd5e8) // Pink background
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                article.title,
                modifier = Modifier.clickable { onArticleClick(article.url) }
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
                Text(if (article.isBookmarked) stringResource(R.string.unbookmark) else stringResource(R.string.bookmark))
            }
        }
    }
}
