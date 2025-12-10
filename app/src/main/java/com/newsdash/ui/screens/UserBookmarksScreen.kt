package com.newsdash.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.newsdash.R
import com.newsdash.model.BookmarksViewModel

@Composable
fun UserBookmarksScreen(
    onBack: () -> Unit,
    onOpen: (String) -> Unit
) {
    val viewModel: BookmarksViewModel = hiltViewModel()
    val bookmarks by viewModel.bookmarks.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)) {
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text(stringResource(R.string.back)) }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(bookmarks.size) { i ->
                val article = bookmarks[i]
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        article.title,
                        modifier = Modifier.clickable { onOpen(article.url) }
                    )
                    Button(onClick = { viewModel.removeBookmark(article.url) }) {
                        Text(stringResource(R.string.remove))
                    }
                }
            }
        }
    }
}
