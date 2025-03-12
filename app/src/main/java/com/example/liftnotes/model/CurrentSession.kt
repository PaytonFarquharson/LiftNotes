package com.example.liftnotes.model

import java.time.DayOfWeek

data class CurrentSession(
    val session: Session,
    val completionDays: List<CompletionDay>
)

data class CompletionDay(
    val dayOfWeek: DayOfWeek,
    val isCompleted: Boolean = false
)