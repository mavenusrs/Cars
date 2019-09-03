package com.sevenpeakssoftware.redaelhadidy.domain.sync

import com.sevenpeakssoftware.redaelhadidy.domain.common.TIME_TO_SYNC
import com.sevenpeakssoftware.redaelhadidy.domain.repository.SynchronizationRepository
import java.util.*

/**
 * Synchronization engine seems to be redundant and we can handle this on data layer but on the sake of
 * scaling application we can use this engine and improve it further more
 */
class SynchronizationEngine(private val synchronizationRepository: SynchronizationRepository) {

    fun syncSuccessfully(apiPath: String, serverTime: Long) {
        updateSyncTime(apiPath, serverTime)
    }

    fun syncFailed(apiPath: String) {
        synchronizationRepository.removeRequestTimeDate(apiPath)
    }

    private fun updateSyncTime(apiPath: String, serverTime: Long) {
        synchronizationRepository.saveRequestTimeDate(apiPath, serverTime)
    }

    /**
     * To eliminate headache from the server, we should set period to get from feeds from server,
     * may be on the future we fix this also by depend on silent GCM to invalidate cashed time
     * to sync from server
     */
    fun shouldSyncWithServer(
        apiPath: String,
        timeToRefreshOnInMillis: Int = TIME_TO_SYNC
    ): Boolean {

        val lastSyncedTimeInMillis = synchronizationRepository.getRequestTimeDate(apiPath)
        if (lastSyncedTimeInMillis != null) {  //is valid
            val expiredCashTimeInMillis = lastSyncedTimeInMillis + timeToRefreshOnInMillis
            val currentTimeInMillis = Calendar.getInstance().timeInMillis

            if (expiredCashTimeInMillis > currentTimeInMillis) {
                updateSyncTime(apiPath, expiredCashTimeInMillis)
                return false
            }
        }

        return synchronizationRepository.isDeviceOnline()
    }
}
