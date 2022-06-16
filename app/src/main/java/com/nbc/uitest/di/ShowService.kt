package com.nbc.uitest.di

import android.app.Activity
import androidx.annotation.RawRes
import com.nbc.uitest.model.Page

interface ShowService {
    suspend fun getShows(
        activity: Activity,
        @RawRes id: Int
    ): Page
}