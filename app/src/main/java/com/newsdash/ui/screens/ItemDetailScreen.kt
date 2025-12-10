package com.newsdash.ui.screens

import android.annotation.SuppressLint
import android.webkit.WebView
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.newsdash.R

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SetJavaScriptEnabled")
@Composable
fun ItemDetailScreen(url: String, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(stringResource(R.string.article))
            })
        }) {
        Button(onClick = onBack) {
            Text(stringResource(R.string.back))
        }
        AndroidView(factory = { ctx ->
            WebView(ctx).apply {
                settings.javaScriptEnabled = true; loadUrl(url)
            }
        })
    }
}
