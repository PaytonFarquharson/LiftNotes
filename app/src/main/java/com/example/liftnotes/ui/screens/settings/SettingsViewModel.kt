package com.example.liftnotes.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.liftnotes.model.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel(
    private val repository: SettingsRepository
) : ViewModel() {

    private val _settings: MutableStateFlow<Settings> =
        MutableStateFlow(repository.getSettings())
    val settings: StateFlow<Settings> get() = _settings

    companion object {
        fun provideFactory(
            repository: SettingsRepository = SettingsRepository()
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SettingsViewModel(repository) as T
            }
        }
    }
}