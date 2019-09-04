package com.sevenpeakssoftware.redaelhadidy.domain.model

data class ArticleContent(
    val id: Int,
    val title: String,
    val dateTime: String,
    val tags: Iterator<Any>?,
    val contents: Iterator<Content>?,
    val ingress: String,
    val image: String,
    val created: Int,
    val changed: Int
)