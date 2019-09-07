package com.sevenpeakssoftware.redaelhadidy.domain.usecase

import com.sevenpeakssoftware.redaelhadidy.domain.model.ArticleContent
import com.sevenpeakssoftware.redaelhadidy.domain.repository.ArticleRepository
import com.sevenpeakssoftware.redaelhadidy.domain.sync.SynchronizationEngine
import com.sevenpeakssoftware.redaelhadidy.domain.usecase.base.FlowableUseCase

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class GetArticleUseCase(
    private val articleRepository: ArticleRepository,
    private val synchronizationEngine: SynchronizationEngine
) : FlowableUseCase<List<ArticleContent>>() {

    private val apiPath = "article/get_articles_list"

    override fun run(): Flowable<List<ArticleContent>> {
        val carFeedsFromCash = getCarFeedFromCash()
        var carFeedsFromServer: Flowable<List<ArticleContent>>? = null

        if (synchronizationEngine.shouldSyncWithServer(apiPath)) {
            carFeedsFromServer = getCarFeedFromServer().map {
                saveSyncTime(it.serverTime)
                cashCarsFeed(it.articlesContent)
                return@map it.articlesContent
            }
        }
        carFeedsFromServer?.apply {
            return Flowable.concat(carFeedsFromCash, carFeedsFromServer)
        }
        return carFeedsFromCash

    }

    private fun getCarFeedFromCash(): Flowable<List<ArticleContent>> {
        return articleRepository.getCashedCarsFeed()
    }

    private fun cashCarsFeed(content: List<ArticleContent>): Completable {
        return articleRepository.cashedCarsFeed(content)
    }

    private fun saveSyncTime(serverTime: Long): Completable {
        synchronizationEngine.syncSuccessfully(apiPath, serverTime)
        return Completable.complete()
    }

    private fun getCarFeedFromServer() = articleRepository.getCarsFeed()
}