package com.example.csgomatches.ui

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(date: Date?) : String? {
    if (date == null) {
        return null
    }

    return SimpleDateFormat("dd.MM HH:mm", Locale.getDefault()).format(date)

//        val closeDate = SimpleDateFormat("EEE HH:mm", Locale.getDefault())
//        val farDate = SimpleDateFormat("dd.MM HH:mm", Locale.getDefault())
//
//        val diff = beginAt.time - Date().time
//        val days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
//        if (days in 0..6) {
//            Log.d(TAG, "getMatchTimeText: $beginAt")
//            return closeDate.format(beginAt)
//        }
//        return farDate.format(beginAt)
}