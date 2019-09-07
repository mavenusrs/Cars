package com.sevenpeakssoftware.redaelhadidy.data.repository

import com.google.gson.reflect.TypeToken
import com.sevenpeakssoftware.redaelhadidy.data.common.DeviceHandler
import com.sevenpeakssoftware.redaelhadidy.data.local.SharedPreferenceHandler
import com.sevenpeakssoftware.redaelhadidy.domain.repository.SynchronizationRepository

class SynchronizationRepositoryImpl(private val sharedPreferencesHandler: SharedPreferenceHandler,
                                    private val deviceHandler: DeviceHandler) : SynchronizationRepository {

    override fun saveRequestTimeDate(requestPath: String, serverTime: Long) {
        sharedPreferencesHandler.save(requestPath, serverTime, object: TypeToken<Long>(){})
    }

    override fun removeRequestTimeDate(requestPath: String) {
        sharedPreferencesHandler.remove(requestPath)
    }

    override fun getLastRequestTimeDate(requestPath: String): Long? {
        return sharedPreferencesHandler.get(requestPath, null, object: TypeToken<Long>(){}) as Long?
    }

    override fun isDeviceOnline(): Boolean {
        return deviceHandler.isDeviceOnline()
    }
}