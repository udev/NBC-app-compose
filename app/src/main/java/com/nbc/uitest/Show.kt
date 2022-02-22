package com.nbc.uitest

import androidx.annotation.DrawableRes

data class Show(
    val id: Long = 0L,
    @DrawableRes
    val imageResource: Int = R.drawable.sample_cover,
    val title: String = "The Office",
    val isLocked: Boolean = true
)