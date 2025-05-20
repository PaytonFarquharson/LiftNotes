package com.example.liftnotes.utils

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

object DateUtils {

    fun getStartOfWeek(startDay: DayOfWeek): LocalDateTime {
        val today = LocalDateTime.now().toLocalDate()
        val currentDow = today.dayOfWeek

        val daysSince = (currentDow.value - startDay.value + 7) % 7
        val targetDate = today.minusDays(daysSince.toLong())

        return LocalDateTime.of(targetDate, LocalTime.MIDNIGHT)
    }
}