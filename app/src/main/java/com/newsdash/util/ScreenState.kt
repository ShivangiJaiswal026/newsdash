package com.newsdash.util

sealed class ScreenState {
    object Feed : ScreenState()
    object UserBookmarks : ScreenState()
    data class ItemDetail(val url: String) : ScreenState()
}