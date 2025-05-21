package com.example.liftnotes.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currentSessions")
data class CurrentSessionIds(
    @PrimaryKey val id: Int = 0,
    val currentSessionIdList: List<Int> = emptyList()
)