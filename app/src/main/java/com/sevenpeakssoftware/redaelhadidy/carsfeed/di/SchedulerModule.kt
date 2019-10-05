package com.sevenpeakssoftware.redaelhadidy.carsfeed.di

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
class SchedulerModule {

    @Singleton
    @Provides
    @Subscriber
    fun provideSubscriber(): Scheduler {
        return Schedulers.io()
    }

    @Singleton
    @Provides
    @Observer
    fun provideObserver(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}