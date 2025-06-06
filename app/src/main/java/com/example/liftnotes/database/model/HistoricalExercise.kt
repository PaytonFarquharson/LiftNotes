package com.example.liftnotes.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "historicalExercises")
data class HistoricalExercise(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val exerciseId: Int,
    val name: String,
    val description: String?,
    val imageId: Int,
    val weight: Float?,
    val sets: Int?,
    val reps: Range?,
    val time: Int?,
    val rating: Rating = Rating.NONE
)

enum class Rating {
    NONE,
    VERY_EASY,
    EASY,
    MEDIUM,
    HARD,
    VERY_HARD
}