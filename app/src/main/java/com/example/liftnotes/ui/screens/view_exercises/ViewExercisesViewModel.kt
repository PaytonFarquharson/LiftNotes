package com.example.liftnotes.ui.screens.view_exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.liftnotes.model.Exercise
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ViewExercisesViewModel(
    private val repository: ViewExercisesRepository
) : ViewModel() {

    private val _currentExercises: MutableStateFlow<List<Exercise>> =
        MutableStateFlow(repository.getCurrentExercises())
    val currentExercises: StateFlow<List<Exercise>> get() = _currentExercises

    fun onCurrentExercisesReorder(currentExercises: List<Exercise>) {
        _currentExercises.value = currentExercises
    }

    companion object {
        fun provideFactory(
            sessionId: String,
            repository: ViewExercisesRepository = ViewExercisesRepository(sessionId)
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ViewExercisesViewModel(repository) as T
            }
        }
    }
}