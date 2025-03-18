package com.example.liftnotes.model

import kotlinx.serialization.Serializable

@Serializable
data class Settings(
    val showDayOfWeek: Boolean
)