package com.sevenpeakssoftware.redaelhadidy.carsfeed

import android.app.Application
import com.sevenpeakssoftware.redaelhadidy.carsfeed.di.ApplicationModule
import com.sevenpeakssoftware.redaelhadidy.carsfeed.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

open class CarsApplication : Application() , HasAndroidInjector{
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build().inject(this)
    }
}