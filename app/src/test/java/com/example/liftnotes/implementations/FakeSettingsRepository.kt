package com.example.liftnotes.implementations

import com.example.liftnotes.repository.interfaces.SettingsRepository
import com.example.liftnotes.repository.model.DataResult
import com.example.liftnotes.database.model.Settings
import com.example.liftnotes.test.testSettings

class FakeSettingsRepository: SettingsRepository {
    override suspend fun getSettings(): DataResult<Settings> {
        return DataResult.Success(testSettings)
    }
}