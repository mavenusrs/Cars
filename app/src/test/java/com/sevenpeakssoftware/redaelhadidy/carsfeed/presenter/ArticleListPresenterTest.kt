package com.sevenpeakssoftware.redaelhadidy.carsfeed.presenter

import com.sevenpeakssoftware.redaelhadidy.carsfeed.model.ArticleContentParcelable
import com.sevenpeakssoftware.redaelhadidy.carsfeed.model.mapper.mapToArticleContentsParcelList
import com.sevenpeakssoftware.redaelhadidy.domain.errorchecker.ArticleException
import com.sevenpeakssoftware.redaelhadidy.domain.model.ArticleContent
import com.sevenpeakssoftware.redaelhadidy.domain.model.ResultState
import com.sevenpeakssoftware.redaelhadidy.domain.model.ResultState.Success
import com.sevenpeakssoftware.redaelhadidy.domain.usecase.GetArticleUseCase
import io.reactivex.Flowable
import io.reactivex.internal.schedulers.TrampolineScheduler
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.*
import kotlin.collections.ArrayList

@RunWith(MockitoJUnitRunner::class)
class ArticleListPresenterTest {

    @Mock
    private lateinit var getArticleUseCase: GetArticleUseCase
    private val observerOnScheduler = TrampolineScheduler.instance()
    private val subscribeOnScheduler = TrampolineScheduler.instance()

    private var listObserver = TestObserver<List<ArticleContentParcelable>>()
    private var errorOserver = TestObserver<ArticleException>()
    private var loadingObserver = TestObserver<Boolean>()

    private lateinit var articlesPresenter: ArticleListPresenter

    @Before
    fun setup() {
        articlesPresenter =
            ArticleListPresenter(getArticleUseCase, subscribeOnScheduler, observerOnScheduler)
        listObserver = articlesPresenter.articlesBehaviourSubjectTrigger.observer().test()
        errorOserver = articlesPresenter.errorBehaviourSubjectTrigger.observer().test()
        loadingObserver = articlesPresenter.loadingBehaviourSubjectTrigger.observer().test()
    }

    @Test
    fun `test happypath when return a value list`() {
        val expectedList = getListOfArticles()
        val expectedListParcel = mapToArticleContentsParcelList(expectedList)

        `when`(getArticleUseCase.execute())
            .thenReturn(Flowable.just(Success<List<ArticleContent>>(expectedList)))

        articlesPresenter.loadArticle()

        loadingObserver.assertValueAt(0, true)
        loadingObserver.assertValueAt(1, false)

        listObserver.assertValueAt(0, expectedListParcel)

    }

    @Test
    fun `test when return empty list`() {
        val emptyList = emptyList<ArticleContent>()
        val expectedListParcel = mapToArticleContentsParcelList(emptyList)

        `when`(getArticleUseCase.execute()).thenReturn(
            Flowable.just(
                Success<List<ArticleContent>>(
                    emptyList
                )
            )
        )

        articlesPresenter.loadArticle()

        loadingObserver.assertValueAt(0, true)
        loadingObserver.assertValueAt(1, false)

        listObserver.assertValueAt(0, expectedListParcel)

    }

    @Test
    fun `test when return Error`() {
        val throwableReturned = Throwable("mocked error happened")
        val error = ArticleException(throwable = throwableReturned)

        `when`(getArticleUseCase.execute())
            .thenReturn(Flowable.just(ResultState.Failed(exception = error)))

        articlesPresenter.loadArticle()

        loadingObserver.assertValueAt(0, true)
        loadingObserver.assertValueAt(1, false)

        errorOserver.assertValue {
            it == error
        }

    }

    @After
    fun teardown() {
        loadingObserver.dispose()
        errorOserver.dispose()
        listObserver.dispose()
    }

    fun getListOfArticles(): ArrayList<ArticleContent> {
        val list = ArrayList<ArticleContent>()

        val firstItem = ArticleContent(
            1,
            "item 1",
            Calendar.getInstance().toString(),
            null, null,
            "Ingress #1",
            "Image url 1", 0, 0
        )
        list.add(firstItem)

        val secondItem = ArticleContent(
            2,
            "item 2",
            Calendar.getInstance().toString(),
            null, null,
            "Ingress #2",
            "Image url 2", 0, 0
        )
        list.add(secondItem)

        return list
    }

}