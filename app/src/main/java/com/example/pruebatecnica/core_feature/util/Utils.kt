package com.example.pruebatecnica.core_feature.util

import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.regex.Pattern.compile

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
        fun isValidEmail(email: String): Boolean {
            val emailRegex = compile(
                "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                        ")+"
            )
            return emailRegex.matcher(email).matches()
        }

        fun isValidPassword(password: String): Boolean {
            if (password.length < 8) return false
            if (password.filter { it.isDigit() }.firstOrNull() == null) return false
            if (password.filter { it.isLetter() }.filter { it.isUpperCase() }.firstOrNull() == null) return false
            if (password.filter { it.isLetter() }.filter { it.isLowerCase() }.firstOrNull() == null) return false
            if (password.filter { !it.isLetterOrDigit() }.firstOrNull() == null) return false

            return true
        }

        fun validateField(field: TextInputLayout, validator: (String) -> Boolean, errorMessage: String): Boolean {
            val input = field.editText!!.text.toString()
            return if (validator(input)) {
                true
            } else {
                field.editText!!.error = errorMessage
                false
            }
        }
    }

}