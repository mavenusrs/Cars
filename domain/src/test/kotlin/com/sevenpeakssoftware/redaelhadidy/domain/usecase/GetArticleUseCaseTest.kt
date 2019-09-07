package com.sevenpeakssoftware.redaelhadidy.domain.usecase

import com.sevenpeakssoftware.redaelhadidy.domain.common.SERVER_GENERAL_ERROR
import com.sevenpeakssoftware.redaelhadidy.domain.errorchecker.ArticleException
import com.sevenpeakssoftware.redaelhadidy.domain.model.ArticleContent
import com.sevenpeakssoftware.redaelhadidy.domain.model.ArticleResponse
import com.sevenpeakssoftware.redaelhadidy.domain.model.ResultState
import com.sevenpeakssoftware.redaelhadidy.domain.repository.ArticleRepository
import com.sevenpeakssoftware.redaelhadidy.domain.sync.SynchronizationEngine
import io.reactivex.Flowable
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class GetArticleUseCaseTest {
    private val expectedEmptyLiveResult = Flowable.empty<ArticleResponse>()
    private val expectedEmptyCashedResult = Flowable.empty<List<ArticleContent>>()
    private val testScheduler = TestScheduler()

    @Mock
    private lateinit var mockedArticleRepository: ArticleRepository
    @Mock
    private lateinit var synchronizationEngine: SynchronizationEngine

    private lateinit var getArticleUseCase: GetArticleUseCase
    private lateinit var sampleLiveArticles: List<ArticleContent>
    private lateinit var sampleCashedArticles: List<ArticleContent>
    private lateinit var expectedLiveResult: Flowable<ArticleResponse>
    private lateinit var expectedCashedResult: Flowable<List<ArticleContent>>

    @Before
    fun setup() {
        getArticleUseCase = GetArticleUseCase(mockedArticleRepository, synchronizationEngine)

        sampleLiveArticles = getListOfLiveContentForTest()
        sampleCashedArticles = getListOfCashedContentForTest()
        expectedLiveResult =
            Flowable.just(ArticleResponse(System.currentTimeMillis(), sampleLiveArticles))
        expectedCashedResult = Flowable.just(sampleCashedArticles)

    }

    @Test
    fun `when get car feeds return error`() {
        val errorCode = SERVER_GENERAL_ERROR

        val articleException = ArticleException(throwable = Exception("error mocked"))

        `when`(mockedArticleRepository.getCashedCarsFeed()).thenReturn(
            Flowable.error(
                articleException
            )
        )

        `when`(
            synchronizationEngine
                .shouldSyncWithServer(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt())
        )
            .thenReturn(false)


        val testSubscriber = getArticleUseCase.execute().test()
        testSubscriber.assertValue {
            val articleException1 = (it as ResultState.Failed<*>).exception
            articleException1.errorCause == errorCode
        }
        testSubscriber.assertComplete()

        testSubscriber.dispose()

    }

    @Test
    fun `get cars feed returns no value if there is no cashed nor online feeds and no sync provided`() {
        `when`(mockedArticleRepository.getCashedCarsFeed()).thenReturn(expectedEmptyCashedResult)

        getArticleUseCase.execute().test()
            .assertNoValues()
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun `get cars feed returns no value if there is no cashed nor online feeds and sync provided`() {
        `when`(mockedArticleRepository.getCarsFeed()).thenReturn(expectedEmptyLiveResult)
        `when`(mockedArticleRepository.getCashedCarsFeed()).thenReturn(expectedEmptyCashedResult)
        `when`(
            synchronizationEngine
                .shouldSyncWithServer(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt())
        )
            .thenReturn(true)

        getArticleUseCase.execute().test()
            .await()
            .assertNoValues()
            .assertNoErrors()
            .assertComplete()
    }

    @Test
    fun `get car feed returns no value if there is no cash feed and should not sync`() {
        `when`(mockedArticleRepository.getCashedCarsFeed()).thenReturn(expectedEmptyCashedResult)
        `when`(
            synchronizationEngine
                .shouldSyncWithServer(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt())
        )
            .thenReturn(false)

        getArticleUseCase.execute().test()
            .await()
            .assertNoValues()
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun `get car feeds returns live data if there is no cash feeds but there is live data and should sync`() {
        `when`(mockedArticleRepository.getCarsFeed())
            .thenReturn(expectedLiveResult.delay(1, TimeUnit.SECONDS, testScheduler))
        `when`(mockedArticleRepository.getCashedCarsFeed())
            .thenReturn(expectedEmptyCashedResult.delay(1, TimeUnit.SECONDS, testScheduler))
        `when`(
            synchronizationEngine
                .shouldSyncWithServer(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt())
        ).thenReturn(true)

        val testSubscriber = getArticleUseCase.execute().test()
        testSubscriber
            .assertNoValues()

        testScheduler.advanceTimeBy(2, TimeUnit.SECONDS)
        testSubscriber
            .assertValue(ResultState.Success(sampleLiveArticles))
            .assertNoErrors()
    }

    @Test
    fun `get cars feed return value if there is cash feeds and no live data but should sync`() {
        `when`(mockedArticleRepository.getCarsFeed())
            .thenReturn(expectedEmptyLiveResult.delay(1, TimeUnit.SECONDS, testScheduler))
        `when`(mockedArticleRepository.getCashedCarsFeed())
            .thenReturn(expectedCashedResult.delay(1, TimeUnit.SECONDS, testScheduler))
        `when`(
            synchronizationEngine
                .shouldSyncWithServer(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt())
        )
            .thenReturn(true)

        val testSubscriber = getArticleUseCase.execute().test()
        testScheduler.advanceTimeBy(5, TimeUnit.SECONDS)

        testSubscriber.assertValue(ResultState.Success(sampleCashedArticles))
            .assertNoErrors()
    }

    /**
     * very important case, which is return cash then return live data
     */
    @Test
    fun `get car feeds return cash then libe value if there is cash feeds and live data and should sync`() {
        expectedCashedResult = expectedCashedResult.delay(1, TimeUnit.SECONDS, testScheduler)
        expectedLiveResult = expectedLiveResult.delay(1, TimeUnit.SECONDS, testScheduler)

        `when`(mockedArticleRepository.getCashedCarsFeed()).thenReturn(expectedCashedResult)
        `when`(mockedArticleRepository.getCarsFeed()).thenReturn(expectedLiveResult)
        `when`(
            synchronizationEngine
                .shouldSyncWithServer(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt())
        ).thenReturn(true)

        val testSubscriber = getArticleUseCase.execute().test()

        testSubscriber.assertNoValues()
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        testSubscriber.assertValueAt(0, ResultState.Success(sampleCashedArticles))

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        testSubscriber.assertValueAt(1, ResultState.Success(sampleLiveArticles))

        // TODO how could I test when cashing live data, after fetch live data

    }

    private fun getListOfCashedContentForTest(): List<ArticleContent> {
        val articleContent1 = ArticleContent(
            1, "content 1",
            "25.05.2018 14:13", null, null, "", "", 1, 2
        )
        val articleContent2 = ArticleContent(
            2, "content 2",
            "25.05.2018 14:13", null, null, "", "", 1, 2
        )
        val articleContent3 = ArticleContent(
            3, "content 3",
            "25.05.2018 14:13", null, null, "", "", 1, 2
        )

        return arrayOf(articleContent1, articleContent2, articleContent3).toList()
    }

    private fun getListOfLiveContentForTest(): List<ArticleContent> {
        val articleContent1 = ArticleContent(
            4, "content 4",
            "25.05.2018 14:13", null, null, "", "", 1, 2
        )
        val articleContent2 = ArticleContent(
            5, "content 5",
            "25.05.2018 14:13", null, null, "", "", 1, 2
        )
        val articleContent3 = ArticleContent(
            6, "content 6",
            "25.05.2018 14:13", null, null, "", "", 1, 2
        )

        return arrayOf(articleContent1, articleContent2, articleContent3).toList()
    }

}