package com.sevenpeakssoftware.redaelhadidy.carsfeed.di

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
class SchedularModule {

    @Singleton
    @Provides
    @Named("subscribe")
    fun provideSubscriber(): Scheduler {
        return Schedulers.io()
    }

    @Singleton
    @Provides
    @Named("observer")
    fun provideObserver(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}