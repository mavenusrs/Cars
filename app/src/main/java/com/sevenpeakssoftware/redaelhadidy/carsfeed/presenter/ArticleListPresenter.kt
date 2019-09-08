package com.sevenpeakssoftware.redaelhadidy.carsfeed.presenter

import com.sevenpeakssoftware.redaelhadidy.carsfeed.model.ArticleContentParcelable
import com.sevenpeakssoftware.redaelhadidy.carsfeed.model.mapper.mapToArticleContentsParcelList
import com.sevenpeakssoftware.redaelhadidy.carsfeed.common.BehaviorSubjectTrigger
import com.sevenpeakssoftware.redaelhadidy.carsfeed.common.addsTo
import com.sevenpeakssoftware.redaelhadidy.domain.errorchecker.ArticleException
import com.sevenpeakssoftware.redaelhadidy.domain.model.ArticleContent
import com.sevenpeakssoftware.redaelhadidy.domain.model.ResultState
import com.sevenpeakssoftware.redaelhadidy.domain.usecase.GetArticleUseCase
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

class ArticleListPresenter @Inject constructor(
    private val useCase: GetArticleUseCase,
    @Named("subscribe") private val subscribeScheduler: Scheduler,
    @Named("observer") private val observerScheduler: Scheduler
) {
    val articlesBehaviourSubjectTrigger =
        BehaviorSubjectTrigger<List<ArticleContentParcelable>>()
    val errorBehaviourSubjectTrigger =
        BehaviorSubjectTrigger<ArticleException>()
    val loadingBehaviourSubjectTrigger =
        BehaviorSubjectTrigger<Boolean>()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadArticle() {
        useCase.execute()
            .subscribeOn(subscribeScheduler)
            .observeOn(observerScheduler)
            .startWith(ResultState.Loading)
            .subscribe {
                it?.apply {
                    handleResponse(it)
                }
            }.addsTo(compositeDisposable)
    }

    fun unbound() {
        compositeDisposable.clear()
    }

    private fun handleResponse(resultState: ResultState<List<ArticleContent>>) {
        when (resultState) {
            is ResultState.Success -> handleLoadedArticlesSuccessfully(resultState.data)
            is ResultState.Failed -> errorLoadingArticle(resultState.exception)
            is ResultState.Loading -> handleLoadingState(resultState)

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

    private fun handleLoadedArticlesSuccessfully(articleContents: List<ArticleContent>) {
        loadingBehaviourSubjectTrigger.trigger(false)
        articlesBehaviourSubjectTrigger.trigger(
            mapToArticleContentsParcelList(
                articleContents
            )
        )
    }
}

