package com.nbc.uitest.model

typealias ShowType = String
typealias ShowTitle = String
typealias Tagline = String
typealias Subtitle = String
typealias LabelBadge = String

sealed class NbcShowType(val type: ShowType) {
    object Live: NbcShowType("Live")
    object Episode: NbcShowType("Episode")
    object Show: NbcShowType("Show")
}

data class Show(
    val type: ShowType, // TODO: create converter factory to convert this from String to NbcShowType
    val title: ShowTitle,
    val image: String, // TODO: create converter factory to convert this from String to Url
    val tagline: Tagline?,
    val subtitle: Subtitle?,
    val labelBadge: LabelBadge?
)