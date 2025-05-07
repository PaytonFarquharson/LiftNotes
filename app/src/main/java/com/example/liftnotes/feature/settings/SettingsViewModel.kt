package com.example.liftnotes.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liftnotes.interfaces.SettingsRepository
import com.example.liftnotes.model.ResultOf
import com.example.liftnotes.model.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SettingsUiState>(SettingsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchSettings()
    }

    private fun fetchSettings() {
        viewModelScope.launch {
            when (val result = repository.getSettings()) {
                is ResultOf.Success -> _uiState.value = SettingsUiState.Success(result.data)
                is ResultOf.Error -> TODO()
            }
        }
    }

    sealed class SettingsUiState {
        object Loading: SettingsUiState()
        data class Success(val settings: Settings): SettingsUiState()
    }
}