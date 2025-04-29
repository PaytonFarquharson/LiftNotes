package com.example.liftnotes.implementations

import com.example.liftnotes.interfaces.SettingsRepository
import com.example.liftnotes.test.testSettings

class SettingsRepositoryImpl() : SettingsRepository {

    override suspend fun getSettings() = testSettings
}