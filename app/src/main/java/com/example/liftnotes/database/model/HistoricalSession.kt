package com.example.liftnotes.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "historicalSessions")
data class HistoricalSession(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dateTime: LocalDateTime,
    val sessionId: Int,
    val name: String,
    val description: String?,
    val imageId: Int,
    val historicalExerciseIds: List<Int>
)