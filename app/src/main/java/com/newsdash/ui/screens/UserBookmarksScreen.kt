package com.newsdash.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun UserBookmarksScreen(onBack: () -> Unit, onOpen: (String) -> Unit) {
    Button(onClick = onBack) { Text("Back") }
    LazyColumn {
        items(0) {}
    }
}