package com.example.thuc.ringgg

import com.example.thuc.data.Alarm
import java.util.*

fun getNextAlarmTimeInMillis(alarm: Alarm): Long {
    val calendar = Calendar.getInstance()
    val currentTime = calendar.timeInMillis

    // Parse time like "07:00 AM"
    val (hourStr, minuteAmPm) = alarm.time.split(":")
    val (minuteStr, amPm) = minuteAmPm.split(" ")
    var hour = hourStr.toInt()
    val minute = minuteStr.toInt()

    if (amPm == "PM" && hour != 12) hour += 12
    if (amPm == "AM" && hour == 12) hour = 0

    calendar.set(Calendar.HOUR_OF_DAY, hour)
    calendar.set(Calendar.MINUTE, minute)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)

    // If time already passed today, go to the next day
    if (calendar.timeInMillis <= currentTime) {
        calendar.add(Calendar.DAY_OF_YEAR, 1)
    }

    return calendar.timeInMillis
}
