package com.sevenpeakssoftware.redaelhadidy.carsfeed.di

import com.sevenpeakssoftware.redaelhadidy.carsfeed.view.MainActivity
import dagger.Component
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        SchedularModule::class,
        DomainModule::class,
        ApiServiceModule::class,
        ActivityBuilder::class
    )
)
interface MainComponenet {
    @Component.Builder
    interface Builder {
        fun applicationModule(applicationModule: ApplicationModule): Builder
        fun domainModule(domainModule: DomainModule): Builder
        fun build(): MainComponenet
    }

    fun inject(mainActivity: MainActivity)
}