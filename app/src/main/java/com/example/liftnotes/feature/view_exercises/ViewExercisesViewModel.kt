package com.example.liftnotes.feature.view_exercises

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liftnotes.interfaces.ViewExercisesRepository
import com.example.liftnotes.model.Exercise
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewExercisesViewModel @Inject constructor(
    private val repository: ViewExercisesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val sessionId: Int = savedStateHandle["id"] ?: -1

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = UiState.Success(repository.getCurrentExercises(sessionId))
        }
    }

    fun onCurrentExercisesReorder(exercises: List<Exercise>) {
        _uiState.value = UiState.Success(exercises)
    }
}

sealed class UiState {
    object Loading: UiState()
    data class Success(val exercises: List<Exercise>): UiState()
}