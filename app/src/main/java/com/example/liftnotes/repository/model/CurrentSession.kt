package com.example.liftnotes.repository.model

import com.example.liftnotes.database.model.Session
import java.time.DayOfWeek

data class CurrentSession(
    val session: Session,
    val completionDays: List<CompletionDay>
)

data class CompletionDay(
    val dayOfWeek: DayOfWeek,
    var isHighlighted: Boolean = false
)