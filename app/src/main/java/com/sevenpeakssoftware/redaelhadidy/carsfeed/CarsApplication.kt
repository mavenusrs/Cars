package com.sevenpeakssoftware.redaelhadidy.carsfeed

import android.app.Application
import com.sevenpeakssoftware.redaelhadidy.carsfeed.di.*

open class CarsApplication : Application() {

    open fun initMainComponent(): MainComponenet {
        return DaggerMainComponenet.builder()
            .applicationModule(ApplicationModule(this))
            .domainModule(DomainModule())
            .build()
    }
}