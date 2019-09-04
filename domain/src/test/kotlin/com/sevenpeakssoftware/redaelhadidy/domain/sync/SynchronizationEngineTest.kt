package com.sevenpeakssoftware.redaelhadidy.domain.sync

import com.sevenpeakssoftware.redaelhadidy.domain.repository.SynchronizationRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class SynchronizationEngineTest{
    private val apiPath = "test"
    private val timeToRefreshInMS = 1000

    @Mock
    private lateinit var synchronizationRepository: SynchronizationRepository
    private lateinit var synchronizationEngine: SynchronizationEngine

    @Before
    fun setup(){
        synchronizationEngine = SynchronizationEngine(synchronizationRepository)
    }

    @Test
    fun `test should not sync when device is offline`(){
        `when`(synchronizationRepository.isDeviceOnline()).thenReturn(false)

        assertFalse(synchronizationEngine.shouldSyncWithServer(apiPath, timeToRefreshInMS))
    }

    @Test
    fun `test should sync first call time device is online`(){
        `when`(synchronizationRepository.isDeviceOnline()).thenReturn(true)
        `when`(synchronizationRepository.getLastRequestTimeDate(apiPath)).thenReturn(null)

        assertTrue(synchronizationEngine.shouldSyncWithServer(apiPath, timeToRefreshInMS))
    }

    @Test
    fun `test should not sync first call time device is offline`(){
        `when`(synchronizationRepository.isDeviceOnline()).thenReturn(false)
        `when`(synchronizationRepository.getLastRequestTimeDate(apiPath)).thenReturn(null)

        assertFalse(synchronizationEngine.shouldSyncWithServer(apiPath, timeToRefreshInMS))
    }

    @Test
    fun `test should not sync when it is not time to refresh and device is online`(){
        val lastRequest = Calendar.getInstance().timeInMillis - (timeToRefreshInMS -1000)

        `when`(synchronizationRepository.isDeviceOnline()).thenReturn(true)
        `when`(synchronizationRepository.getLastRequestTimeDate(apiPath)).thenReturn(lastRequest)

        assertFalse(synchronizationEngine.shouldSyncWithServer(apiPath, timeToRefreshInMS))
    }

    @Test
    fun `test should sync when it is time to refresh and device is online`(){
        val lastRequest = Calendar.getInstance().timeInMillis - (timeToRefreshInMS +1000)

        `when`(synchronizationRepository.isDeviceOnline()).thenReturn(true)
        `when`(synchronizationRepository.getLastRequestTimeDate(apiPath)).thenReturn(lastRequest)

        assertTrue(synchronizationEngine.shouldSyncWithServer(apiPath, timeToRefreshInMS))
    }

    @Test
    fun `test should not sync when it is time to refresh and device is offline`(){
        val lastRequest = Calendar.getInstance().timeInMillis - (timeToRefreshInMS +1000)

        `when`(synchronizationRepository.isDeviceOnline()).thenReturn(false)
        `when`(synchronizationRepository.getLastRequestTimeDate(apiPath)).thenReturn(lastRequest)

        assertFalse(synchronizationEngine.shouldSyncWithServer(apiPath, timeToRefreshInMS))
    }

    @Test
    fun `test should sync when it is not time to refresh and device is offline`(){
        val lastRequest = Calendar.getInstance().timeInMillis - (timeToRefreshInMS - 1000)

        `when`(synchronizationRepository.isDeviceOnline()).thenReturn(false)
        `when`(synchronizationRepository.getLastRequestTimeDate(apiPath)).thenReturn(lastRequest)

        assertFalse(synchronizationEngine.shouldSyncWithServer(apiPath, timeToRefreshInMS))
    }

}