package com.sevenpeakssoftware.redaelhadidy.carsfeed.model

import com.sevenpeakssoftware.redaelhadidy.domain.model.ArticleContent

/**
 *
 * Map entities to Domain models
 */

fun mapToArticleContentsParcelIterator(contents: Iterator<ArticleContent>): Iterator<ArticleContentParcelable> {
    contents.apply {
        val articleContentsParcel = ArrayList<ArticleContentParcelable>()

        forEach {
            articleContentsParcel.add(mapToArticleContent(it))
        }
        return articleContentsParcel.iterator()
    }
}

fun mapToArticleContent(articleContent: ArticleContent): ArticleContentParcelable {
    articleContent.apply {
        return ArticleContentParcelable(id, title, dateTime, ingress, image)
    }
}


