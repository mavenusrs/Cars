package com.sevenpeakssoftware.redaelhadidy.carsfeed.di

import com.sevenpeakssoftware.redaelhadidy.carsfeed.view.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, SchedularModule::class,
    DomainModule::class, ApiServiceModule::class))
interface MainComponenet{
    fun jnject(mainActivity: MainActivity)
}