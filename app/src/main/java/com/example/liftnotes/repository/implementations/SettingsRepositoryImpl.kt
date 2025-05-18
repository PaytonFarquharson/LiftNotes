package com.example.liftnotes.repository.implementations

import com.example.liftnotes.database.LiftNotesDao
import com.example.liftnotes.repository.model.DataResult
import com.example.liftnotes.database.model.Settings
import com.example.liftnotes.repository.interfaces.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dao: LiftNotesDao
) : SettingsRepository {

    override fun getSettings(): Flow<DataResult<Settings>> = dao.getSettings()
        .map { settings ->
            settings?.let { DataResult.Success(settings) }
                ?: DataResult.Error("Settings not found")
        }
        .onStart { emit(DataResult.Loading) }
        .catch { e -> emit(DataResult.Error(e.message)) }
}