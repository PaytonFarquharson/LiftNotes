package com.example.liftnotes.ui.screens.view_sessions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.liftnotes.model.Session
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ViewSessionsViewModel(
    private val repository: ViewSessionsRepository
) : ViewModel() {

    private val _currentSessions: MutableStateFlow<List<Session>> =
        MutableStateFlow(repository.getCurrentSessions())
    val currentSessions: StateFlow<List<Session>> get() = _currentSessions

    companion object {
        fun provideFactory(
            repository: ViewSessionsRepository = ViewSessionsRepository()
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ViewSessionsViewModel(repository) as T
            }
        }
    }
}