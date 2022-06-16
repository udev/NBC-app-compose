package com.nbc.uitest.model

data class Shelf(
    val title: String,
    val type: String,
    val items: List<Show>
)