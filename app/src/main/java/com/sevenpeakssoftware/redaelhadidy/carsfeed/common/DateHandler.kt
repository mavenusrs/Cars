package com.sevenpeakssoftware.redaelhadidy.carsfeed.common

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

class DateHandler(private val context: Context){
    private val timeZone = TimeZone.getTimeZone("UTC")

    fun toFormattedDateTimeUTC(timeInMilliSecond: Long): String {
        val calendar = Calendar.getInstance(timeZone)
        calendar.timeInMillis = timeInMilliSecond

        val outputFormat = getOutputFormat(calendar.time)
        val outDataFormat = SimpleDateFormat(outputFormat, Locale.getDefault())
        outDataFormat.timeZone = timeZone

        return outDataFormat.format(calendar.time)
    }

    private fun getOutputFormat(sourceTime: Date): String {
        TimeZone.setDefault(timeZone)

        val calendar = Calendar.getInstance(timeZone)
        calendar.time = sourceTime

        val currentYear = calendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)
        val is24 = android.text.format.DateFormat.is24HourFormat(context)

        return "dd MMMM${if (currentYear) "" else " yyyy"},${if (is24) " HH:mm" else " hh:mm aa"}"
    }

}