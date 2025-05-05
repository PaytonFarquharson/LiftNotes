package com.example.liftnotes.feature.view_exercises

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liftnotes.interfaces.ViewExercisesRepository
import com.example.liftnotes.model.Exercise
import com.example.liftnotes.ui.navigation.WorkoutRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewExercisesViewModel @Inject constructor(
    private val repository: ViewExercisesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val sessionId: Int = savedStateHandle[WorkoutRoute.ARG_EXERCISE_ID] ?: -1

    private val _uiState: MutableStateFlow<ViewExercisesUiState> =
        MutableStateFlow(ViewExercisesUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<ViewExercisesUiEffect>()
    val effect = _effect.asSharedFlow()

    init {
        viewModelScope.launch {
            _uiState.value = ViewExercisesUiState.Success(repository.getCurrentExercises(sessionId))
        }
    }

    fun onUiEvent(event: ViewExercisesUiEvent) {
        when (event) {
            is ViewExercisesUiEvent.BackPressed -> {
                viewModelScope.launch { _effect.emit(ViewExercisesUiEffect.NavigateBack) }
            }

            is ViewExercisesUiEvent.CurrentExercisesReordered -> {
                _uiState.value = ViewExercisesUiState.Success(event.exercises)
            }

            is ViewExercisesUiEvent.AddClicked -> {
                TODO()
            }

            is ViewExercisesUiEvent.EditClicked -> {
                TODO()
            }

            is ViewExercisesUiEvent.DeleteClicked -> {
                TODO()
            }
        }
    }
}

sealed class ViewExercisesUiState {
    object Loading : ViewExercisesUiState()
    data class Success(val exercises: List<Exercise>) : ViewExercisesUiState()
}

sealed class ViewExercisesUiEffect {
    object NavigateBack : ViewExercisesUiEffect()
}

sealed class ViewExercisesUiEvent {
    object BackPressed : ViewExercisesUiEvent()
    data class CurrentExercisesReordered(val exercises: List<Exercise>) : ViewExercisesUiEvent()
    object AddClicked : ViewExercisesUiEvent()
    data class EditClicked(val exercise: Exercise) : ViewExercisesUiEvent()
    data class DeleteClicked(val exercise: Exercise) : ViewExercisesUiEvent()
}