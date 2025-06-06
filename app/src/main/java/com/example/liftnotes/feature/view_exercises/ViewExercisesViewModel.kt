package com.example.liftnotes.feature.view_exercises

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liftnotes.R
import com.example.liftnotes.database.model.Exercise
import com.example.liftnotes.database.model.Range
import com.example.liftnotes.navigation.WorkoutRoute
import com.example.liftnotes.repository.interfaces.WorkoutRepository
import com.example.liftnotes.repository.model.DataResult
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
                    viewModelScope.launch {
                        repository.updateSession(
                            state.data.session.copy(
                                exerciseIds = exerciseIds
                            )
                        )
                    }
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
        (_bottomSheetState.value as? EditExerciseBottomSheetState.Edit)?.let { state ->
            when (event) {
                is EditExerciseBottomSheetEvent.NameChanged -> {
                    _bottomSheetState.value = state.copy(name = event.name, nameError = null)
                }

                is EditExerciseBottomSheetEvent.DescriptionChanged -> {
                    _bottomSheetState.value = state.copy(description = event.description)
                }

                is EditExerciseBottomSheetEvent.IconChanged -> {
                    _bottomSheetState.value = state.copy(imageId = event.imageId)
                }

                is EditExerciseBottomSheetEvent.MinRepsChanged -> {
                    val minReps = event.reps?.takeIf { it > 0 } ?: 0
                    _bottomSheetState.value = if (minReps == 0) {
                        state.copy(reps = null, repsError = null)
                    } else {
                        state.copy(
                            reps = state.reps?.copy(min = minReps) ?: Range(min = minReps, max = null),
                            repsError = null
                        )
                    }
                }

                is EditExerciseBottomSheetEvent.MaxRepsChanged -> {
                    val maxReps = event.reps?.takeIf { it > 0 }
                    _bottomSheetState.value =
                        state.copy(
                            reps = state.reps?.copy(max = maxReps) ?: Range(min = 0, max = maxReps),
                            repsError = null
                        )
                }
                is EditExerciseBottomSheetEvent.SetsChanged -> {
                    val newSets = event.sets?.takeIf { it > 0 }
                    _bottomSheetState.value = state.copy(sets = newSets)
                }
                is EditExerciseBottomSheetEvent.TimeChanged -> {
                    val newTime = event.time?.takeIf { it > 0 }
                    _bottomSheetState.value = state.copy(time = newTime)
                }
                is EditExerciseBottomSheetEvent.WeightIncrementChanged -> {
                    _bottomSheetState.value = state.copy(weightIncrement = event.increment)
                }
                is EditExerciseBottomSheetEvent.WeightChanged -> {
                    _bottomSheetState.value = state.copy(weight = event.weight)
                }
                is EditExerciseBottomSheetEvent.Close -> {
                    _bottomSheetState.value = EditExerciseBottomSheetState.Closed
                }

                is EditExerciseBottomSheetEvent.Save -> {
                    validateAndUpdateExercise(
                        Exercise(
                            name = state.name,
                            description = state.description,
                            imageId = state.imageId,
                            weight = state.weight,
                            sets = state.sets,
                            reps = state.reps,
                            time = state.time
                        )
                    )
                }
            }
        }
    }

    private fun validateAndUpdateExercise(exercise: Exercise) {
        var isValid = true
        if (exercise.name.isBlank()) {
            (_bottomSheetState.value as? EditExerciseBottomSheetState.Edit)?.let { state ->
                _bottomSheetState.value = state.copy(
                    nameError = "Name cannot be empty"
                )
            }
            isValid = false
        }
        exercise.reps?.let {
            if (it.min < 0 || (it.max != null && it.max <= it.min)) {
                (_bottomSheetState.value as? EditExerciseBottomSheetState.Edit)?.let { state ->
                    _bottomSheetState.value = state.copy(
                        repsError = "Invalid rep range"
                    )
                }
                isValid = false
            }
        }

        if (!isValid) return
        updateExercise(exercise)
        _bottomSheetState.value = EditExerciseBottomSheetState.Closed
    }

    private fun updateExercise(exercise: Exercise) {
        (uiState.value as? DataResult.Success)?.let { dataResult ->
            viewModelScope.launch {
                val exerciseId = repository.updateExercise(exercise)
                val exerciseIds = dataResult.data.session.exerciseIds
                if (!exerciseIds.contains(exerciseId)) {
                    repository.updateSession(
                        dataResult.data.session.copy(
                            exerciseIds = exerciseIds + exerciseId
                        )
                    )
                }
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
        val nameError: String? = null,
        val description: String = "",
        val imageId: Int = R.drawable.ic_empty,
        val weight: Float? = null,
        val weightIncrement: Float? = null,
        val sets: Int? = null,
        val reps: Range? = null,
        val repsError: String? = null,
        val time: Int? = null,
    ) : EditExerciseBottomSheetState()
}

sealed class EditExerciseBottomSheetEvent {
    data class NameChanged(val name: String) : EditExerciseBottomSheetEvent()
    data class DescriptionChanged(val description: String) : EditExerciseBottomSheetEvent()
    data class IconChanged(val imageId: Int) : EditExerciseBottomSheetEvent()
    data class WeightChanged(val weight: Float?) : EditExerciseBottomSheetEvent()
    data class WeightIncrementChanged(val increment: Float?) : EditExerciseBottomSheetEvent()
    data class SetsChanged(val sets: Int?) : EditExerciseBottomSheetEvent()
    data class MinRepsChanged(val reps: Int?) : EditExerciseBottomSheetEvent()
    data class MaxRepsChanged(val reps: Int?) : EditExerciseBottomSheetEvent()
    data class TimeChanged(val time: Int?) : EditExerciseBottomSheetEvent()
    object Close : EditExerciseBottomSheetEvent()
    object Save : EditExerciseBottomSheetEvent()
}
