package com.example.liftnotes.model

import androidx.annotation.DrawableRes
import com.example.liftnotes.R
import kotlinx.serialization.Serializable

@Serializable
data class Exercise(
    val id: String,
    val name: String,
    val description: String? = null,
    @DrawableRes val imageId: Int = R.drawable.ic_empty,
    val weight: Float? = null,
    val sets: Range? = null,
    val reps: Range? = null,
    val time: Float? = null,
    val rating: Rating = Rating.NONE
)

@Serializable
data class Range(
    val min: Int,
    val max: Int? = null
)

enum class Rating {
    NONE,
    VERY_EASY,
    EASY,
    MEDIUM,
    HARD,
    VERY_HARD
}