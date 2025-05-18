package com.example.liftnotes.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class Settings(
    @PrimaryKey val id: Int = 0,
    val settings: String
)