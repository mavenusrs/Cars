package com.sevenpeakssoftware.redaelhadidy.carsfeed.di

import com.sevenpeakssoftware.redaelhadidy.carsfeed.CarsApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, ApplicationModule::class,
    SchedulerModule::class, ApiServiceModule::class, InjectionBuilder::class])
interface AppComponent {

    fun inject(app: CarsApplication)

    @Component.Builder
    interface Builder {

        fun applicationModule(applicationModule: ApplicationModule): Builder
        fun schedulerModule(schedulerModule: SchedulerModule): Builder
        fun build(): AppComponent
    }
}
