package com.sevenpeakssoftware.redaelhadidy.data.entity.mapper

import com.sevenpeakssoftware.redaelhadidy.data.entity.ArticleResponseEntity
import com.sevenpeakssoftware.redaelhadidy.data.entity.ArticleContentEntity
import com.sevenpeakssoftware.redaelhadidy.data.entity.ContentEntity

import com.sevenpeakssoftware.redaelhadidy.domain.model.ArticleContent
import com.sevenpeakssoftware.redaelhadidy.domain.model.ArticleResponse
import com.sevenpeakssoftware.redaelhadidy.domain.model.Content

/**
 *
 * Map entities to Domain models
 */
fun mapToArticleResponse(articleResponseEntity: ArticleResponseEntity): ArticleResponse {
    articleResponseEntity.apply {
        return ArticleResponse(serverTime, mapToArticleContentsList(articleContentEntity))
    }
}

fun mapToArticleContentsList(contents: List<ArticleContentEntity>): List<ArticleContent> {
    val articleContents = ArrayList<ArticleContent>()

    contents.map {
        articleContents.add(mapToArticleContent(it))
    }
    return articleContents
}

fun mapToArticleContent(articleContentEntity: ArticleContentEntity): ArticleContent {
    articleContentEntity.apply {
        return ArticleContent(
            id, title, dateTime, tags,
            contents?.let { mapToContentList(it).toList() }, ingress, image, created, changed
        )
    }
}

fun mapToContentList(contentEntities: List<ContentEntity>): List<Content> {
    val contents = ArrayList<Content>()

    contentEntities.map {
        contents.add(mapToContent(it))
    }
    return contents
}

fun mapToContent(contentEntity: ContentEntity): Content {
    contentEntity.apply {
        return Content(id, type, subject, description)
    }
}
