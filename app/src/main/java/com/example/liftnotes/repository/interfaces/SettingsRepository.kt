package com.example.liftnotes.repository.interfaces

import com.example.liftnotes.repository.model.DataResult
import com.example.liftnotes.database.model.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getSettings(): Flow<DataResult<Settings>>
}