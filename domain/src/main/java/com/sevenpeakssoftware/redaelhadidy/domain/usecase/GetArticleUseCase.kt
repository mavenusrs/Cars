package com.sevenpeakssoftware.redaelhadidy.domain.usecase

import com.sevenpeakssoftware.redaelhadidy.domain.model.ArticleContent
import com.sevenpeakssoftware.redaelhadidy.domain.repository.ArticleRepository
import com.sevenpeakssoftware.redaelhadidy.domain.sync.SynchronizationEngine
import com.sevenpeakssoftware.redaelhadidy.domain.usecase.base.SingleUseCase

import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

open class GetArticleUseCase(
    private val articleRepository: ArticleRepository,
    private val synchronizationEngine: SynchronizationEngine
) : SingleUseCase<List<ArticleContent>>() {

    private val apiPath = "article/get_articles_list"

    override fun run(): Flowable<List<ArticleContent>> {
        val carFeedsFromCash = getCarFeedFromCash().subscribeOn(Schedulers.io())
        if (synchronizationEngine.shouldSyncWithServer(apiPath)) {
            return carFeedsFromCash.concatWith(getCarFeedFromServer().map {
                saveSyncTime(it.serverTime)
                cashCarsFeed(it.articlesContent)
                return@map it.articlesContent
            })
        } else {
            return carFeedsFromCash.toFlowable()
        }

    }

    private fun getCarFeedFromCash(): Single<List<ArticleContent>> {
        return articleRepository.getCashedCarsFeed()
    }

    private fun cashCarsFeed(content: List<ArticleContent>) {
        return articleRepository.cashedCarsFeed(content)
    }

    private fun saveSyncTime(serverTime: Long) {
        synchronizationEngine.syncSuccessfully(
            apiPath,
            serverTime
        )
    }

    private fun getCarFeedFromServer() = articleRepository.getCarsFeed()
}