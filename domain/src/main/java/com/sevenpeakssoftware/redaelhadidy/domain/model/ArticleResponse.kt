package com.sevenpeakssoftware.redaelhadidy.domain.model

data class ArticleResponse(val serverTime: Long, val articlesContent: Iterator<ArticleContent>)