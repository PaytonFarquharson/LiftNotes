package com.example.liftnotes.feature.view_exercises

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liftnotes.R
import com.example.liftnotes.database.model.Exercise
import com.example.liftnotes.repository.model.DataResult
import com.example.liftnotes.repository.interfaces.WorkoutRepository
import com.example.liftnotes.navigation.WorkoutRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewExercisesViewModel @Inject constructor(
    private val repository: WorkoutRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val sessionId: Int = savedStateHandle[WorkoutRoute.ARG_EXERCISE_ID] ?: -1

    val uiState = repository.getSessionExercises(sessionId)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            DataResult.Loading
        )

    private val _effect = MutableSharedFlow<ViewExercisesUiEffect>(extraBufferCapacity = 1)
    val effect = _effect.asSharedFlow()

    private val _bottomSheetState: MutableStateFlow<EditExerciseBottomSheetState> =
        MutableStateFlow(EditExerciseBottomSheetState.Closed)
    val bottomSheetState = _bottomSheetState.asStateFlow()

    fun onUiEvent(event: ViewExercisesUiEvent) {
        when (event) {
            is ViewExercisesUiEvent.BackPressed -> {
                viewModelScope.launch { _effect.emit(ViewExercisesUiEffect.NavigateBack) }
            }

            is ViewExercisesUiEvent.CurrentExercisesReordered -> {
                (uiState.value as? DataResult.Success)?.let { state ->
                    val exerciseIds = event.exercises.map { it.id }
                    viewModelScope.launch { repository.updateSession(state.data.session.copy(exerciseIds = exerciseIds)) }
                }
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
