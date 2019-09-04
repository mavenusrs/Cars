package com.sevenpeakssoftware.redaelhadidy.data.entity.mapper

import com.sevenpeakssoftware.redaelhadidy.data.entity.ArticleContentEntity
import com.sevenpeakssoftware.redaelhadidy.data.entity.ContentEntity

import com.sevenpeakssoftware.redaelhadidy.domain.model.ArticleContent
import com.sevenpeakssoftware.redaelhadidy.domain.model.Content

/**
 *
 * Map entities to Domain models
 */

fun mapToArticleContentsIteratorEntity(contents: Iterator<ArticleContent>): Iterator<ArticleContentEntity> {
    contents.apply {
        val articleContentEntitis = ArrayList<ArticleContentEntity>()

        forEach {
            articleContentEntitis.add(mapToArticleContentEntity(it))
        }
        return articleContentEntitis.iterator()
    }
}

fun mapToArticleContentEntity(articleContent: ArticleContent): ArticleContentEntity {
    articleContent.apply {
        return ArticleContentEntity(
            id, title, dateTime, tags, contents?.let { mapToContentIteratorEntity(it) },
            ingress, image, created, changed
        )
    }
}

fun mapToContentIteratorEntity(contents: Iterator<Content>): Iterator<ContentEntity> {
    val contentEntities = ArrayList<ContentEntity>()

    contents.apply {
        forEach {
            contentEntities.add(mapToContentEntity(it))
        }
        return contentEntities.iterator()
    }
}

fun mapToContentEntity(content: Content): ContentEntity {
    content.apply {
        return ContentEntity(id, type, subject, description)
    }
}
