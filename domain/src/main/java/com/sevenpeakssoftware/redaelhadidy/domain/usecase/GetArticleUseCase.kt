package com.sevenpeakssoftware.redaelhadidy.domain.usecase

import com.sevenpeakssoftware.redaelhadidy.domain.model.ArticleContent
import com.sevenpeakssoftware.redaelhadidy.domain.repository.ArticleRepository
import com.sevenpeakssoftware.redaelhadidy.domain.sync.SynchronizationEngine
import com.sevenpeakssoftware.redaelhadidy.domain.usecase.base.SingleUseCase

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.intellij.lang.annotations.Flow

open class GetArticleUseCase(
    private val articleRepository: ArticleRepository,
    private val synchronizationEngine: SynchronizationEngine
) : SingleUseCase<List<ArticleContent>>() {

    private val apiPath = "article/get_articles_list"

    override fun run(): Flowable<List<ArticleContent>> {
        val carFeedsFromCash = getCarFeedFromCash().subscribeOn(Schedulers.io())
        var carFeedsFromServer: Single<List<ArticleContent>>? = null

        if (synchronizationEngine.shouldSyncWithServer(apiPath)) {
            carFeedsFromServer = getCarFeedFromServer().map {
                saveSyncTime(it.serverTime)
                cashCarsFeed(it.articlesContent)
                return@map it.articlesContent
            }.subscribeOn(Schedulers.io())
        }

        return Single.concat(carFeedsFromCash, carFeedsFromServer)
    }

    private fun getCarFeedFromCash(): Single<List<ArticleContent>> {
        return articleRepository.getCashedCarsFeed()
    }

    private fun cashCarsFeed(content: List<ArticleContent>): Completable {
        return articleRepository.cashedCarsFeed(content)
    }

    private fun saveSyncTime(serverTime: Long): Completable {
        return Completable.fromAction{synchronizationEngine.syncSuccessfully(apiPath, serverTime)}
    }

    private fun getCarFeedFromServer() = articleRepository.getCarsFeed()
}