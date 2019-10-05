package com.sevenpeakssoftware.redaelhadidy.carsfeed.di

import android.app.Activity
import javax.inject.Inject

@ActivityScope
class ActivityScopeUtil @Inject constructor(private val activity: Activity){

    override fun toString(): String {
        return "ActivityScopeUtil: ${hashCode()} , Activity: ${activity.hashCode()}"
    }
}