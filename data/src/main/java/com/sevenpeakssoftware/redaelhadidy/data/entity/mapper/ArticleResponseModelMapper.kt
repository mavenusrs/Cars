package com.sevenpeakssoftware.redaelhadidy.data.entity.mapper

import com.sevenpeakssoftware.redaelhadidy.data.entity.ArticleContentEntity
import com.sevenpeakssoftware.redaelhadidy.data.entity.ContentEntity

import com.sevenpeakssoftware.redaelhadidy.domain.model.ArticleContent
import com.sevenpeakssoftware.redaelhadidy.domain.model.Content

/**
 *
 * Map entities to Domain models
 */

fun mapToArticleContentsListEntity(contents: List<ArticleContent>): List<ArticleContentEntity> {
    val articleContentEntitis = ArrayList<ArticleContentEntity>()

    contents.map {
        articleContentEntitis.add(mapToArticleContentEntity(it))
    }

    return articleContentEntitis
}


fun mapToArticleContentEntity(articleContent: ArticleContent): ArticleContentEntity {
    articleContent.apply {
        return ArticleContentEntity(
            id,
            title,
            dateTime,
            tags?.toList(),
            contents?.let { mapToContentListEntity(it).toList() },
            ingress,
            image,
            created,
            changed
        )
    }
}

fun mapToContentListEntity(contents: List<Content>): List<ContentEntity> {
    val contentEntities = ArrayList<ContentEntity>()

    contents.map {
        contentEntities.add(mapToContentEntity(it))
    }
    return contentEntities
}

fun mapToContentEntity(content: Content): ContentEntity {
    content.apply {
        return ContentEntity(id, type, subject, description)
    }
}
