package com.example.liftnotes.model

import androidx.annotation.DrawableRes
import com.example.liftnotes.R

data class Session(
    val id: String,
    val name: String,
    val description: String? = null,
    @DrawableRes val imageId: Int = R.drawable.ic_empty,
    val exercises: List<Exercise> = emptyList()
)
