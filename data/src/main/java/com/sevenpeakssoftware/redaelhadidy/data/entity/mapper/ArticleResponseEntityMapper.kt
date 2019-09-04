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
        return ArticleResponse(serverTime, mapToArticleContentsIterator(articleContentEntity))
    }
}

fun mapToArticleContentsIterator(contents: Iterator<ArticleContentEntity>): Iterator<ArticleContent> {
    contents.apply {
        val articleContents = ArrayList<ArticleContent>()

        forEach {
            articleContents.add(mapToArticleContent(it))
        }
        return articleContents.iterator()
    }
}

fun mapToArticleContent(articleContentEntity: ArticleContentEntity): ArticleContent {
    articleContentEntity.apply {
        return ArticleContent(id, title, dateTime, tags,
            contents?.let { mapToContentIterator(it) }, ingress, image, created, changed)
    }
}

fun mapToContentIterator(contentEntities: Iterator<ContentEntity>): Iterator<Content> {
    val contents = ArrayList<Content>()

    contentEntities.apply {
        forEach {
            contents.add(mapToContent(it))
        }
        return contents.iterator()
    }
}

fun mapToContent(contentEntity: ContentEntity): Content {
    contentEntity.apply {
        return Content(id, type, subject, description)
    }
}
