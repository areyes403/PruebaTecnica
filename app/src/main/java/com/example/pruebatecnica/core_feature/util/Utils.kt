package com.example.pruebatecnica.core_feature.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class Utils {
    companion object{
        fun convertLongToDate(time: Long): String {
            val date = Date(time)
            val format = SimpleDateFormat("yyyy-MM-dd")
            return format.format(date)
        }
        fun convertCompleteDateToDate(inputDate: String): String {
            val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val targetFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            val date = originalFormat.parse(inputDate)
            return targetFormat.format(date)
        }
        fun convertDateToDouble(date:String):Long {
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            format.timeZone = TimeZone.getTimeZone("UTC")
            val date = format.parse(date)
            return date.time
        }
    }

}