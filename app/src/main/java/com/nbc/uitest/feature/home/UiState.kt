package com.nbc.uitest.feature.home

import com.nbc.uitest.model.Page

sealed class UiState(val loading:Boolean= true) {
    object Loading: UiState()
    data class Success(
        val page: Page
    ): UiState(false)
    data class Error(
        val cause: Throwable
    ): UiState(false)
}