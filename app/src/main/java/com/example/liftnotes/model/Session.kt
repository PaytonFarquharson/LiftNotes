package com.example.liftnotes.model

import androidx.annotation.DrawableRes
import com.example.liftnotes.R
import kotlinx.serialization.Serializable
import java.time.DayOfWeek

@Serializable
data class Session(
    val id: Int,
    val name: String,
    val description: String? = null,
    @DrawableRes val imageId: Int = R.drawable.ic_empty,
    val daysOfWeek: List<DayOfWeek> = emptyList(),
    val exercises: List<Exercise> = emptyList()
)
