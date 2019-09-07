package com.sevenpeakssoftware.redaelhadidy.carsfeed.model.mapper

import com.sevenpeakssoftware.redaelhadidy.carsfeed.model.ArticleContentParcelable
import com.sevenpeakssoftware.redaelhadidy.domain.model.ArticleContent

/**
 *
 * Map entities to Domain models
 */

fun mapToArticleContentsParcelList(contents: List<ArticleContent>): List<ArticleContentParcelable> {
    val articleContentsParcel = ArrayList<ArticleContentParcelable>()

    contents.map {
        articleContentsParcel.add(
            mapToArticleContent(
                it
            )
        )
    }
    return articleContentsParcel
}

fun mapToArticleContent(articleContent: ArticleContent): ArticleContentParcelable {
    articleContent.apply {
        return ArticleContentParcelable(
            id,
            title,
            dateTime,
            ingress,
            image
        )
    }
}


