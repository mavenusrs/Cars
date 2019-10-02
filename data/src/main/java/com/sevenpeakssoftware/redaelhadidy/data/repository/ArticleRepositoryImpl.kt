package com.sevenpeakssoftware.redaelhadidy.data.repository

import com.sevenpeakssoftware.redaelhadidy.data.entity.mapper.mapToArticleContentsList
import com.sevenpeakssoftware.redaelhadidy.data.entity.mapper.mapToArticleContentsListEntity
import com.sevenpeakssoftware.redaelhadidy.data.entity.mapper.mapToArticleResponse
import com.sevenpeakssoftware.redaelhadidy.data.remote.ArticleFeedApi
import com.sevenpeakssoftware.redaelhadidy.data.local.ArticleFeedDAO
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

    override fun getCashedCarsFeed(): Flowable<List<ArticleContent>> {
        return articleFeedDAO.getCarsFeed().toFlowable().map {
            //Simulate delay
            Thread.sleep(1000)
            mapToArticleContentsList(it)
        }
    }

    override fun cashedCarsFeed(articleContents: List<ArticleContent>): Completable {
        articleFeedDAO.clearCarsFeed()
        articleFeedDAO.insertCarsFeeds(mapToArticleContentsListEntity(articleContents))
        return Completable.complete()
    }
}
