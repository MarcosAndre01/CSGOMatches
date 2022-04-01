package com.example.csgomatches.ui

import android.content.Context
import android.text.format.DateUtils
import com.example.csgomatches.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun formatDate(date: Date?, context: Context): String? {
    if (date == null) {
        return null
    }

    val hour = SimpleDateFormat(" HH:mm", Locale.getDefault()).format(date)
    if (DateUtils.isToday(date.time)) {
        return context.getString(R.string.today) + "," + hour
    }

    val daysDifference = TimeUnit.DAYS.convert(date.time - Date().time, TimeUnit.MILLISECONDS)
    if (daysDifference in 0..6) {
        val day = SimpleDateFormat("EEE", Locale.getDefault()).format(date)
            .replaceFirstChar { it.uppercase() }.replace(".", ",")
        return day + hour
    }

    return SimpleDateFormat("dd.MM HH:mm", Locale.getDefault()).format(date)
}