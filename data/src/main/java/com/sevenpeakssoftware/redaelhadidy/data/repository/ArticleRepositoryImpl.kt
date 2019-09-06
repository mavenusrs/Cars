package com.sevenpeakssoftware.redaelhadidy.data.repository

import com.sevenpeakssoftware.redaelhadidy.data.entity.mapper.mapToArticleContentsIterator
import com.sevenpeakssoftware.redaelhadidy.data.entity.mapper.mapToArticleContentsIteratorEntity
import com.sevenpeakssoftware.redaelhadidy.data.entity.mapper.mapToArticleResponse
import com.sevenpeakssoftware.redaelhadidy.data.local.ArticleFeedApi
import com.sevenpeakssoftware.redaelhadidy.data.remote.ArticleFeedDAO
import com.sevenpeakssoftware.redaelhadidy.domain.model.ArticleContent
import com.sevenpeakssoftware.redaelhadidy.domain.model.ArticleResponse
import com.sevenpeakssoftware.redaelhadidy.domain.repository.ArticleRepository

import io.reactivex.Completable
import io.reactivex.Flowable

class ArticleRepositoryImpl(
    private val articleFeedApi: ArticleFeedApi,
    private val articleFeedDAO: ArticleFeedDAO
) : ArticleRepository {

    override fun getCarsFeed(): Flowable<ArticleResponse> {
        return articleFeedApi.getCarsFeed().map {
            mapToArticleResponse(it)
        }
    }

    override fun getCashedCarsFeed(): Flowable<Iterator<ArticleContent>> {
        return articleFeedDAO.getCarsFeed().map {
            mapToArticleContentsIterator(it)
        }
    }

    override fun cashedCarsFeed(articleContents: Iterator<ArticleContent>): Completable {
        return articleFeedDAO.clearCarsFeed()
            .concatWith(
                articleFeedDAO
                    .insertCarsFeed(mapToArticleContentsIteratorEntity(articleContents))
            )
    }
}