package com.example.liftnotes.database.model

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.liftnotes.R
import java.time.DayOfWeek

@Entity(tableName = "sessions")
data class Session(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String?,
    @DrawableRes val imageId: Int = R.drawable.ic_empty,
    val daysOfWeek: List<DayOfWeek> = emptyList(),
    val exerciseIds: List<Int> = emptyList()
)