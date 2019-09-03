package com.sevenpeakssoftware.redaelhadidy.data.entity.mapper

import com.sevenpeakssoftware.redaelhadidy.data.entity.ArticleResponseEntity
import com.sevenpeakssoftware.redaelhadidy.data.entity.Content

import com.sevenpeakssoftware.redaelhadidy.domain.model.ArticleContent
import com.sevenpeakssoftware.redaelhadidy.domain.model.ArticleResponse

fun mapToArticleResponse(articleResponseEntity: ArticleResponseEntity): ArticleResponse {
    articleResponseEntity.apply {
        return ArticleResponse(serverTime, mapToContentIterator(content))
    }
}

fun mapToContentIterator(contents: Iterator<Content>): Iterator<ArticleContent> {
    contents.apply {
        val contentsPoko = ArrayList<ArticleContent>()

        forEach {
            contentsPoko.add(mapToContent(it))
        }
        return contentsPoko.iterator()
    }
}

fun mapToContent(content: Content): ArticleContent {
    content.apply {
        return ArticleContent(id, title, dateTime, tags, ingress, image, created, changed)
    }
}
