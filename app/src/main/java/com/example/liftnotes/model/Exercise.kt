package com.example.liftnotes.model

import androidx.annotation.DrawableRes
import com.example.liftnotes.R

data class Exercise(
    val id: String,
    val name: String,
    val description: String? = null,
    @DrawableRes val imageId: Int = R.drawable.ic_empty,
    val sets: List<Set> = emptyList(),
    val reps: Reps? = null,
    val rating: Rating = Rating.NONE
)

data class Set(
    val weight: Float = 0f,
    val description: String? = null,
    val reps: Reps? = null,
    val rating: Rating = Rating.NONE
)

data class Reps(
    val minReps: Int,
    val maxReps: Int = 0
)

enum class Rating {
    NONE,
    VERY_EASY,
    EASY,
    MEDIUM,
    HARD,
    VERY_HARD
}