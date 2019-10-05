package com.sevenpeakssoftware.redaelhadidy.carsfeed.di

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SingletonUtil @Inject constructor(){

    override fun toString(): String {
        return "SingletonUtil: ${hashCode()}"
    }
}