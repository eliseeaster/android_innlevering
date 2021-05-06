package no.kristiania.android_innlevering.utils

import java.text.SimpleDateFormat
import java.util.*

class Extentions {
    companion object{

        fun  dateTime(time: Long, formatString: String): String{
            val format = SimpleDateFormat(formatString)
            return format.format(Date(time))
        }
    }
}