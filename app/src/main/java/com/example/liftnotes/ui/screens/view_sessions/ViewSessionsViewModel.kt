package com.example.liftnotes.ui.screens.view_sessions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.liftnotes.model.CurrentSession
import com.example.liftnotes.model.Session
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ViewSessionsViewModel(
    private val repository: ViewSessionsRepository
) : ViewModel() {

    private val _currentSessions: MutableStateFlow<List<CurrentSession>> =
        MutableStateFlow(repository.getCurrentSessions())
    val currentSessions: StateFlow<List<CurrentSession>> get() = _currentSessions

    private val _bottomSheetState: MutableStateFlow<BottomSheetState> =
        MutableStateFlow(BottomSheetState.Closed)
    val bottomSheetState: StateFlow<BottomSheetState> get() = _bottomSheetState

    fun onCurrentSessionsReorder(currentSessions: List<CurrentSession>) {
        _currentSessions.value = currentSessions
    }

    fun onAddClick() {
        _bottomSheetState.value = BottomSheetState.Add
    }

    fun onEditClick(session: Session) {
        _bottomSheetState.value = BottomSheetState.Edit(session)
    }

    fun onBottomSheetClose() {
        _bottomSheetState.value = BottomSheetState.Closed
    }

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

sealed class BottomSheetState {
    object Closed: BottomSheetState()
    object Add: BottomSheetState()
    data class Edit(val session: Session): BottomSheetState()
}