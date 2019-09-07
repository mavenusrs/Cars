package com.sevenpeakssoftware.redaelhadidy.carsfeed.di

import android.content.Context
import com.sevenpeakssoftware.redaelhadidy.data.common.DeviceHandler
import com.sevenpeakssoftware.redaelhadidy.data.local.SharedPreferenceHandler
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return context
    }

    @Singleton
    @Provides
    fun proviceSharedPreferenceHandler(context: Context): SharedPreferenceHandler {
        return SharedPreferenceHandler(context)
    }

    @Singleton
    @Provides
    fun proviceDeviceHandler(context: Context): DeviceHandler {
        return DeviceHandler(context)
    }
}