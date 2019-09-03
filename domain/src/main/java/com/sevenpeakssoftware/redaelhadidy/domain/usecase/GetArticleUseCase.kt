package com.sevenpeakssoftware.redaelhadidy.domain.usecase

import com.sevenpeakssoftware.redaelhadidy.domain.model.ArticleContent
import com.sevenpeakssoftware.redaelhadidy.domain.repository.ArticleRepository
import com.sevenpeakssoftware.redaelhadidy.domain.sync.SynchronizationEngine
import com.sevenpeakssoftware.redaelhadidy.domain.usecase.base.FlowableUseCase

import io.reactivex.schedulers.Schedulers
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

class GetArticleUseCase(
        private val articleRepository: ArticleRepository,
        private val synchronizationEngine: SynchronizationEngine
) : FlowableUseCase<Iterator<ArticleContent>>() {

    private val apiPath = "article/get_articles_list"

    override fun execute(): Flowable<Iterator<ArticleContent>> {
        val carFeedFromCashMaybe = getCarFeedFromCash()
        var carFeedsFromServer: Maybe<Iterator<ArticleContent>>? = null

        if (synchronizationEngine.shouldSyncWithServer(apiPath)) {
            carFeedsFromServer = getCarFeedFromServer().map {
                saveSyncTime(it.serverTime)
                cashCarsFeed(it.articlesContent)
                return@map it.articlesContent
            }.subscribeOn(Schedulers.io())
        }
        carFeedsFromServer?.apply {
            return Maybe.concat(carFeedFromCashMaybe, carFeedsFromServer)
        }
        return carFeedFromCashMaybe.toFlowable()

    }

    private fun getCarFeedFromCash(): Maybe<Iterator<ArticleContent>> {
        return articleRepository.getCashedCarsFeed().filter {
            it.hasNext()
        }
    }

    private fun cashCarsFeed(content: Iterator<ArticleContent>) =
        articleRepository.cashedCarsFeed(content)

    private fun saveSyncTime(serverTime: Long): Completable {
        synchronizationEngine.syncSuccessfully(apiPath, serverTime)
        return Completable.complete()
    }

    private fun getCarFeedFromServer() = articleRepository.getCarsFeed()
}