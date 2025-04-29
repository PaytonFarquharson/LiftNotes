package com.example.liftnotes.interfaces

import com.example.liftnotes.model.Settings

interface SettingsRepository {

    suspend fun getSettings(): Settings
}