package com.sevenpeakssoftware.redaelhadidy.carsfeed.view

import com.sevenpeakssoftware.redaelhadidy.carsfeed.CarsApplication
import com.sevenpeakssoftware.redaelhadidy.carsfeed.di.ApplicationModule
import com.sevenpeakssoftware.redaelhadidy.carsfeed.di.DaggerMainComponenet
import com.sevenpeakssoftware.redaelhadidy.carsfeed.di.MainComponenet

class TestCarsApplication : CarsApplication() {

    override fun initMainComponent(): MainComponenet {
        return DaggerMainComponenet.builder()
            .applicationModule(ApplicationModule(this))
            .domainModule(MockDomainModule())
            .build()
    }
}