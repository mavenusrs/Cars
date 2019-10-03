package com.sevenpeakssoftware.redaelhadidy.domain.model

data class ArticleContent(
    val id: Int?,
    val title: String,
    val dateTime: String,
    val tags: List<Any>?,
    val contents: List<Content>?,
    val ingress: String,
    val image: String,
    val created: Long,
    val changed: Long
)