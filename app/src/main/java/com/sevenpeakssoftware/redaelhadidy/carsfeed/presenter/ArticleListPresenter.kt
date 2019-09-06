package com.sevenpeakssoftware.redaelhadidy.carsfeed.presenter

import com.sevenpeakssoftware.redaelhadidy.carsfeed.model.ArticleContentParcelable
import com.sevenpeakssoftware.redaelhadidy.carsfeed.model.mapToArticleContentsParcelIterator
import com.sevenpeakssoftware.redaelhadidy.carsfeed.common.BehaviorSubjectTrigger
import com.sevenpeakssoftware.redaelhadidy.carsfeed.common.addsTo
import com.sevenpeakssoftware.redaelhadidy.domain.errorchecker.ArticleException
import com.sevenpeakssoftware.redaelhadidy.domain.model.ArticleContent
import com.sevenpeakssoftware.redaelhadidy.domain.model.ResultState
import com.sevenpeakssoftware.redaelhadidy.domain.usecase.GetArticleUseCase
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

class ArticleListPresenter(
    private val useCase: GetArticleUseCase,
    private val subscribeScheduler: Scheduler,
    private val observerScheduler: Scheduler
) {
    val articlesBehaviourSubjectTrigger =
        BehaviorSubjectTrigger<Iterator<ArticleContentParcelable>>()
    val errorBehaviourSubjectTrigger =
        BehaviorSubjectTrigger<ArticleException>()
    val loadingBehaviourSubjectTrigger =
        BehaviorSubjectTrigger<Boolean>()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadArticle() {
        handleLoadingState(ResultState.Loading)
        useCase.execute().startWith(ResultState.Loading)
            .subscribeOn(subscribeScheduler)
            .observeOn(observerScheduler)
            .subscribe {
                it?.apply {
                    handleResponse(it)
                }
            }.addsTo(compositeDisposable)
    }

    fun unbound() {
        compositeDisposable.clear()
    }

    private fun handleResponse(resultState: ResultState<Iterator<ArticleContent>>) {
        when (resultState) {
            is ResultState.Success -> handleLoadedArticlesSuccessfully(resultState.data)
            is ResultState.Failed -> errorLoadingArticle(resultState.exception)
        }
    }

    private fun handleLoadingState(loadingState: ResultState.Loading?) {
        loadingState?.apply {
            loadingBehaviourSubjectTrigger.trigger(true)
        }
    }

    private fun errorLoadingArticle(failedState: ArticleException) {
        loadingBehaviourSubjectTrigger.trigger(false)
        errorBehaviourSubjectTrigger.trigger(failedState)
    }

    private fun handleLoadedArticlesSuccessfully(articleContents: Iterator<ArticleContent>) {
        loadingBehaviourSubjectTrigger.trigger(false)
        articlesBehaviourSubjectTrigger.trigger(mapToArticleContentsParcelIterator(articleContents))
    }
}

