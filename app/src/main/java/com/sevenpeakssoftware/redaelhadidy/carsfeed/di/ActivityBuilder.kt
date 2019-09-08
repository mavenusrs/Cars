package com.sevenpeakssoftware.redaelhadidy.carsfeed.di

import com.sevenpeakssoftware.redaelhadidy.carsfeed.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}