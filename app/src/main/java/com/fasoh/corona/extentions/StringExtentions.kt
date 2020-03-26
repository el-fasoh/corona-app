package com.fasoh.corona.extentions

import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun String.dateToMilliSeconds() : Long{
    val f = SimpleDateFormat("MM/dd/yy", Locale.getDefault())
    return try {
        val date = f.parse(this)
        date.time
    } catch (e: ParseException) {
        Timber.e(e)
        0
    }
}
