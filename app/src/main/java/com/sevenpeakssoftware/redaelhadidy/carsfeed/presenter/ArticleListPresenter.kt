package com.sevenpeakssoftware.redaelhadidy.carsfeed.presenter

import com.sevenpeakssoftware.redaelhadidy.carsfeed.model.ArticleContentParcelable
import com.sevenpeakssoftware.redaelhadidy.carsfeed.model.mapper.mapToArticleContentsParcelList
import com.sevenpeakssoftware.redaelhadidy.carsfeed.common.BehaviorSubjectTrigger
import com.sevenpeakssoftware.redaelhadidy.carsfeed.common.addsTo
import com.sevenpeakssoftware.redaelhadidy.carsfeed.di.Observer
import com.sevenpeakssoftware.redaelhadidy.carsfeed.di.Subscriber
import com.sevenpeakssoftware.redaelhadidy.domain.errorchecker.ArticleException
import com.sevenpeakssoftware.redaelhadidy.domain.model.ArticleContent
import com.sevenpeakssoftware.redaelhadidy.domain.model.ResultState
import com.sevenpeakssoftware.redaelhadidy.domain.usecase.GetArticleUseCase
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ArticleListPresenter @Inject constructor(
    private val useCase: GetArticleUseCase,
    @Subscriber private val subscribeScheduler: Scheduler,
    @Observer private val observerScheduler: Scheduler
) {
    val articlesBehaviourSubjectTrigger =
        BehaviorSubjectTrigger<List<ArticleContentParcelable>>()
    val errorBehaviourSubjectTrigger =
        BehaviorSubjectTrigger<ArticleException>()
    val loadingBehaviourSubjectTrigger =
        BehaviorSubjectTrigger<Boolean>()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadArticle() {
        handleLoadingState(true)
        useCase.execute()
            .subscribeOn(subscribeScheduler)
            .observeOn(observerScheduler)
            .subscribe({
                it?.apply {
                    handleResponse(it)
                }
            }, {
                it?.apply {
                    handleResponse(ResultState.Failed(ArticleException(throwable = it)))
                }
            }).addsTo(compositeDisposable)

    }

    fun unbound() {
        compositeDisposable.clear()
    }

    private fun handleResponse(resultState: ResultState<List<ArticleContent>>) {
        handleLoadingState(resultState is ResultState.Loading)

        when (resultState) {
            is ResultState.Success -> handleLoadedArticlesSuccessfully(resultState.data)
            is ResultState.Failed -> errorLoadingArticle(resultState.exception)
        }
    }

    private fun handleLoadingState(loadingState: Boolean) {
        loadingBehaviourSubjectTrigger.trigger(loadingState)
    }

    private fun errorLoadingArticle(failedState: ArticleException) {
        errorBehaviourSubjectTrigger.trigger(failedState)
    }

    private fun handleLoadedArticlesSuccessfully(articleContents: List<ArticleContent>) {
        loadingBehaviourSubjectTrigger.trigger(false)
        articlesBehaviourSubjectTrigger.trigger(
            mapToArticleContentsParcelList(
                articleContents
            )
        )
    }
}

