package com.example.liftnotes.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liftnotes.repository.model.DataResult
import com.example.liftnotes.repository.interfaces.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    repository: SettingsRepository
) : ViewModel() {

    val uiState = repository.getSettings()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            DataResult.Loading
        )


    fun onUiEvent(event: SettingsUiEvent) {

    }

    sealed class SettingsUiEvent {

    }
}