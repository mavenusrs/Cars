package com.sevenpeakssoftware.redaelhadidy.carsfeed.di


import com.sevenpeakssoftware.redaelhadidy.carsfeed.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module
abstract class InjectionBuilder {
    @ActivityScope
    @ContributesAndroidInjector(modules = [AndroidSupportInjectionModule::class,
        DomainModule::class])
    abstract fun bindMainActivity(): MainActivity
}