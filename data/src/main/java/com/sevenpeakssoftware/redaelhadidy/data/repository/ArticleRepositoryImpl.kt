package com.sevenpeakssoftware.redaelhadidy.data.repository

import com.sevenpeakssoftware.redaelhadidy.data.entity.mapper.mapToArticleContentsList
import com.sevenpeakssoftware.redaelhadidy.data.entity.mapper.mapToArticleContentsListEntity
import com.sevenpeakssoftware.redaelhadidy.data.entity.mapper.mapToArticleResponse
import com.sevenpeakssoftware.redaelhadidy.data.remote.ArticleFeedApi
import com.sevenpeakssoftware.redaelhadidy.data.local.ArticleFeedDAO
import com.sevenpeakssoftware.redaelhadidy.domain.model.ArticleContent
import com.sevenpeakssoftware.redaelhadidy.domain.model.ArticleResponse
import com.sevenpeakssoftware.redaelhadidy.domain.repository.ArticleRepository

import io.reactivex.Single

class ArticleRepositoryImpl(
    private val articleFeedApi: ArticleFeedApi,
    private val articleFeedDAO: ArticleFeedDAO
) : ArticleRepository {

    override fun getCarsFeed(): Single<ArticleResponse> {
        return articleFeedApi.getCarsFeed().map {
            mapToArticleResponse(it)
        }
    }

    override fun getCashedCarsFeed(): Single<List<ArticleContent>> {
        return articleFeedDAO.getCarsFeed().map {
            mapToArticleContentsList(it)
        }
    }

    override fun cashedCarsFeed(articleContents: List<ArticleContent>){
            articleFeedDAO.clearCarsFeed()
            articleFeedDAO.insertCarsFeeds(mapToArticleContentsListEntity(articleContents))
    }
}
