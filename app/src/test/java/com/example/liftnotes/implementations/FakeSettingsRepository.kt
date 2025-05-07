package com.example.liftnotes.implementations

import com.example.liftnotes.interfaces.SettingsRepository
import com.example.liftnotes.model.ResultOf
import com.example.liftnotes.model.Settings
import com.example.liftnotes.test.testSettings

class FakeSettingsRepository: SettingsRepository {
    override suspend fun getSettings(): ResultOf<Settings> {
        return ResultOf.Success(testSettings)
    }
}