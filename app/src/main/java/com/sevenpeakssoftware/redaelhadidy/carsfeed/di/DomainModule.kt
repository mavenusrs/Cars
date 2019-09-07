package com.sevenpeakssoftware.redaelhadidy.carsfeed.di

import android.content.Context
import androidx.room.Room
import com.sevenpeakssoftware.redaelhadidy.data.DATABASE_NAME
import com.sevenpeakssoftware.redaelhadidy.data.common.DeviceHandler
import com.sevenpeakssoftware.redaelhadidy.data.local.ArticleFeedDAO
import com.sevenpeakssoftware.redaelhadidy.data.local.ArticleFeedDatabase
import com.sevenpeakssoftware.redaelhadidy.data.local.SharedPreferenceHandler
import com.sevenpeakssoftware.redaelhadidy.data.remote.ArticleFeedApi
import com.sevenpeakssoftware.redaelhadidy.data.repository.ArticleRepositoryImpl
import com.sevenpeakssoftware.redaelhadidy.data.repository.SynchronizationRepositoryImpl
import com.sevenpeakssoftware.redaelhadidy.domain.repository.ArticleRepository
import com.sevenpeakssoftware.redaelhadidy.domain.repository.SynchronizationRepository
import com.sevenpeakssoftware.redaelhadidy.domain.sync.SynchronizationEngine
import com.sevenpeakssoftware.redaelhadidy.domain.usecase.GetArticleUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides
    fun provideDatabase(context: Context): ArticleFeedDatabase {
        return Room.databaseBuilder(context, ArticleFeedDatabase::class.java, DATABASE_NAME).build()
    }

    @Provides
    fun provideArticleFeedDAP(articleFeedDatabase: ArticleFeedDatabase): ArticleFeedDAO {
        return articleFeedDatabase.articleDAO()
    }

    @Singleton
    @Provides
    fun provideArticleRepository(
        articleFeedApi: ArticleFeedApi,
        articleFeedDAO: ArticleFeedDAO): ArticleRepository {
        return ArticleRepositoryImpl(articleFeedApi, articleFeedDAO)
    }

    @Singleton
    @Provides
    fun provideSynchronizationRepository(
        sharedPreferencesHandler: SharedPreferenceHandler,
        deviceHandler: DeviceHandler): SynchronizationRepository {
        return SynchronizationRepositoryImpl(sharedPreferencesHandler, deviceHandler)
    }

    @Singleton
    @Provides
    fun provideGetArticleUseCase(
        articleRepository: ArticleRepository,
        synchronizationRepository: SynchronizationRepository
    ): GetArticleUseCase {
        return GetArticleUseCase(
            articleRepository,
            SynchronizationEngine(synchronizationRepository)
        )
    }
}