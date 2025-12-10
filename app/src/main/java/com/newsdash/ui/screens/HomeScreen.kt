package com.newsdash.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.newsdash.util.ScreenState

@Composable
fun HomeScreen() {
    var current by remember { mutableStateOf<ScreenState>(ScreenState.Feed) }

    when (val s = current) {
        ScreenState.Feed -> UserFeedScreen(
            onArticleClick = { url -> current = ScreenState.ItemDetail(url) },
            onBookmarksClick = { current = ScreenState.UserBookmarks })

        is ScreenState.ItemDetail -> ItemDetailScreen(
            url = s.url,
            onBack = { current = ScreenState.Feed })

        ScreenState.UserBookmarks -> UserBookmarksScreen(
            onBack = { current = ScreenState.Feed },
            onOpen = { url -> current = ScreenState.ItemDetail(url) }
        )
    }
}