package com.example.liftnotes.implementations

import com.example.liftnotes.interfaces.SettingsRepository
import com.example.liftnotes.model.ResultOf
import com.example.liftnotes.model.Settings
import com.example.liftnotes.test.testSettings
import kotlinx.coroutines.delay

class SettingsRepositoryImpl() : SettingsRepository {

    override suspend fun getSettings() : ResultOf<Settings> {
        try {
            delay(1000)
            return ResultOf.Success(testSettings)
        } catch (e: Exception) {
            return ResultOf.Error(e.message)
        }
    }
}