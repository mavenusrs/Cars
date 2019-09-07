package com.sevenpeakssoftware.redaelhadidy.carsfeed.common

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

class DateHandler(private val context: Context){
    fun toFormatedDateTime(sourceTimeInString: String): String {
        val sourceFormat = "dd.MM.yyyy HH:mm"
        val sourceDataFormat = SimpleDateFormat(sourceFormat, Locale.getDefault())
        val sourceDateTime = sourceDataFormat.parse(sourceTimeInString)

        val outputFormat = getOutputFormat(context, sourceDateTime)
        val outDataFormat = SimpleDateFormat(outputFormat, Locale.getDefault())

        return outDataFormat.format(sourceDateTime)
    }

    fun getOutputFormat(context: Context, sourceTime: Date): String? {
        val calendar = Calendar.getInstance()
        calendar.time = sourceTime
        val currentYear = calendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)
        val is24 = android.text.format.DateFormat.is24HourFormat(context)

        return "dd MMMM${if (currentYear) "" else " yyyy"}, HH:mm${if (is24) "" else " aa"}"
    }
}