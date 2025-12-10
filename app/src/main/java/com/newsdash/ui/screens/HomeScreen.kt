package com.newsdash.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.newsdash.util.ScreenState

@Composable
fun HomeScreen() {
    val currentScreen =
        remember { mutableStateOf<ScreenState>(ScreenState.Feed) }

    when (val screen = currentScreen.value) {
        ScreenState.Feed -> UserFeedScreen(
            onArticleClick = { url -> currentScreen.value = ScreenState.ItemDetail(url) },
            onBookmarksClick = { currentScreen.value = ScreenState.UserBookmarks }
        )

        is ScreenState.ItemDetail -> ItemDetailScreen(
            url = screen.url,
            onBack = { currentScreen.value = ScreenState.Feed }
        )

        ScreenState.UserBookmarks -> UserBookmarksScreen(
            onBack = { currentScreen.value = ScreenState.Feed },
            onOpen = { url -> currentScreen.value = ScreenState.ItemDetail(url) }
        )
    }
}