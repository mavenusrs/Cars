package com.sevenpeakssoftware.redaelhadidy.domain.usecase

import com.sevenpeakssoftware.redaelhadidy.domain.model.ArticleContent
import com.sevenpeakssoftware.redaelhadidy.domain.model.ArticleResponse
import com.sevenpeakssoftware.redaelhadidy.domain.repository.ArticleRepository
import com.sevenpeakssoftware.redaelhadidy.domain.sync.SynchronizationEngine
import io.reactivex.Maybe
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doThrow
import org.mockito.runners.MockitoJUnitRunner
import kotlin.collections.ArrayList

@RunWith(MockitoJUnitRunner::class)
class GetArticleUseCaseTest {
    @Mock
    private lateinit var mockedArticleRepository: ArticleRepository
    @Mock
    private lateinit var synchronizationEngine: SynchronizationEngine
    private lateinit var getArticleUseCase: GetArticleUseCase


    @Before
    fun setup() {
        getArticleUseCase = GetArticleUseCase(mockedArticleRepository, synchronizationEngine)
    }

    //TODO refactor unit test to be applied to the new change

    @Test
    fun `test get cars feed return null if there is no feed`() {
        val expectedMaybeResult = Maybe.empty<ArticleResponse>()
        `when`(mockedArticleRepository.getCarsFeed()).thenReturn(expectedMaybeResult)

        getArticleUseCase.execute().test()
            .assertNoValues()
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun `test get cars feed return error`() {
        doThrow(Exception::class.java)
            .`when`(mockedArticleRepository).getCarsFeed()

        try {
            getArticleUseCase.execute()
            Assert.fail()
        } catch (exception: Exception) {
            Assert.assertEquals(exception.javaClass, Exception::class.java)
        }
    }

    @Test
    fun `test get cars feed return result if there is feeds`() {
        val iteratorOfArticleContentPoko= ArrayList<ArticleContent>(2)
        iteratorOfArticleContentPoko.add(ArticleContent())
        iteratorOfArticleContentPoko.add(ArticleContent())

        val articleIterator = iteratorOfArticleContentPoko.iterator()


        val expectedResult = Maybe.create<ArticleResponse> {
            it.onSuccess(ArticleResponse(0L, articleIterator))
        }

        `when`(mockedArticleRepository.getCarsFeed()).thenReturn(expectedResult)

        getArticleUseCase.execute().test()
            .assertValue(articleIterator)
            .assertComplete()
            .assertNoErrors()
    }
}