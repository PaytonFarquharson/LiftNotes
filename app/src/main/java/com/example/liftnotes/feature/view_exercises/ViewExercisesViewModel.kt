package com.example.liftnotes.feature.view_exercises

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liftnotes.R
import com.example.liftnotes.interfaces.ViewExercisesRepository
import com.example.liftnotes.model.Exercise
import com.example.liftnotes.model.ResultOf
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

    private val _bottomSheetState: MutableStateFlow<EditExerciseBottomSheetState> =
        MutableStateFlow(EditExerciseBottomSheetState.Closed)
    val bottomSheetState = _bottomSheetState.asStateFlow()

    init {
        fetchCurrentExercises()
    }

    private fun fetchCurrentExercises() {
        viewModelScope.launch {
            when(val result = repository.getCurrentExercises(sessionId)) {
                is ResultOf.Success -> _uiState.value = ViewExercisesUiState.Success(result.data)
                is ResultOf.Error -> _uiState.value = ViewExercisesUiState.Error(result.message)
            }
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
                _bottomSheetState.value = EditExerciseBottomSheetState.Edit()
            }

            is ViewExercisesUiEvent.EditClicked -> {
                TODO()
            }

            is ViewExercisesUiEvent.DeleteClicked -> {
                TODO()
            }
        }
    }

    fun onBottomSheetEvent(event: EditExerciseBottomSheetEvent) {
        when (event) {
            is EditExerciseBottomSheetEvent.DescriptionChanged -> TODO()
            is EditExerciseBottomSheetEvent.IconChanged -> TODO()
            is EditExerciseBottomSheetEvent.NameChanged -> TODO()
            is EditExerciseBottomSheetEvent.Close -> {
                _bottomSheetState.value = EditExerciseBottomSheetState.Closed
            }
            is EditExerciseBottomSheetEvent.Save -> {

            }
        }
    }
}

sealed class ViewExercisesUiState {
    object Loading : ViewExercisesUiState()
    data class Success(val exercises: List<Exercise>) : ViewExercisesUiState()
    data class Error(val message: String?) : ViewExercisesUiState()
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

sealed class EditExerciseBottomSheetState {
    object Closed : EditExerciseBottomSheetState()
    data class Edit(
        val id: Int? = null,
        val name: String = "",
        val description: String = "",
        val imageId: Int = R.drawable.ic_empty,
    ) : EditExerciseBottomSheetState()
}

sealed class EditExerciseBottomSheetEvent {
    data class NameChanged(val name: String) : EditExerciseBottomSheetEvent()
    data class DescriptionChanged(val description: String) : EditExerciseBottomSheetEvent()
    data class IconChanged(val imageId: Int) : EditExerciseBottomSheetEvent()
    object Close : EditExerciseBottomSheetEvent()
    object Save : EditExerciseBottomSheetEvent()
}
