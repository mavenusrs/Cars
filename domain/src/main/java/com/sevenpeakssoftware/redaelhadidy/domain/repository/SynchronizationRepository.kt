package com.sevenpeakssoftware.redaelhadidy.domain.repository

interface SynchronizationRepository {
    fun saveRequestTimeDate(requestPath: String, serverTime: Long)
    fun removeRequestTimeDate(requestPath: String)
    fun getRequestTimeDate(requestPath: String): Long?
    fun isDeviceOnline(): Boolean
}