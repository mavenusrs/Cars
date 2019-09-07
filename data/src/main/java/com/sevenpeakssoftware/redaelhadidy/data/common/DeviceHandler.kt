package com.sevenpeakssoftware.redaelhadidy.data.common

import android.content.Context
import android.net.ConnectivityManager

class DeviceHandler(private val context: Context) {

    fun isDeviceOnline(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        activeNetworkInfo?.apply {
            return isConnected
        }
        return false
    }
}