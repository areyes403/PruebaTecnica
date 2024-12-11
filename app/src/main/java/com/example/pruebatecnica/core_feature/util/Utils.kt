package com.example.pruebatecnica.core_feature.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

            val date = originalFormat.parse(inputDate)  // Convierte la cadena a un objeto Date
            return targetFormat.format(date)  // Devuelve solo la parte de la fecha en formato deseado
        }
    }

}