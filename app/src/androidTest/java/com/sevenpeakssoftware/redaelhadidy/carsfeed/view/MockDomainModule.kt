package com.sevenpeakssoftware.redaelhadidy.carsfeed.view

import com.sevenpeakssoftware.redaelhadidy.carsfeed.di.DomainModule
import com.sevenpeakssoftware.redaelhadidy.domain.repository.ArticleRepository
import com.sevenpeakssoftware.redaelhadidy.domain.repository.SynchronizationRepository
import com.sevenpeakssoftware.redaelhadidy.domain.usecase.GetArticleUseCase
import dagger.Module
import io.reactivex.Flowable
import org.mockito.Mockito
import org.mockito.Mockito.`when`

@Module
class MockDomainModule : DomainModule() {

    override fun provideGetArticleUseCase(
        articleRepository: ArticleRepository,
        synchronizationRepository: SynchronizationRepository
    ): GetArticleUseCase {

        val getArticleUseCase = Mockito.mock(GetArticleUseCase::class.java)
        `when`(getArticleUseCase.run())
            .thenReturn(Flowable.empty())
        return getArticleUseCase
    }

}