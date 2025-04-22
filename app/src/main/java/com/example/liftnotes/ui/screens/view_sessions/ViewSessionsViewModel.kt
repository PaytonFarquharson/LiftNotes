package com.example.liftnotes.ui.screens.view_sessions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.liftnotes.R
import com.example.liftnotes.model.CompletionDay
import com.example.liftnotes.model.CurrentSession
import com.example.liftnotes.model.Session
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.DayOfWeek

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
        _bottomSheetState.value = BottomSheetState.Edit(completionDays = getCompletionDays())
    }

    fun onEditClick(session: Session) {
        _bottomSheetState.value = BottomSheetState.Edit(
            session.id,
            session.name,
            session.description.orEmpty(),
            session.imageId,
            getCompletionDays(session.daysOfWeek)
        )
    }

    private fun getCompletionDays(daysOfWeek: List<DayOfWeek> = emptyList()): List<CompletionDay> {
        val completionsDays = mutableListOf<CompletionDay>()
        for (day in DayOfWeek.entries) {
            completionsDays.add(CompletionDay(day, daysOfWeek.contains(day)))
        }
        return completionsDays
    }

    fun onBottomSheetEvent(event: BottomSheetEvent) {
        when(event) {
            is BottomSheetEvent.NameChanged -> {
                (_bottomSheetState.value as? BottomSheetState.Edit)?.let {
                    _bottomSheetState.value = it.copy(name = event.name)
                }
            }
            is BottomSheetEvent.DescriptionChanged -> {
                (_bottomSheetState.value as? BottomSheetState.Edit)?.let {
                    _bottomSheetState.value = it.copy(description = event.description)
                }
            }
            is BottomSheetEvent.IconChanged -> {
                (_bottomSheetState.value as? BottomSheetState.Edit)?.let {
                    _bottomSheetState.value = it.copy(imageId = event.imageId)
                }
            }
            is BottomSheetEvent.DayChanged -> {
                (_bottomSheetState.value as? BottomSheetState.Edit)?.let {
                    val completionDays: MutableList<CompletionDay> = mutableListOf()
                    for (completionDay in it.completionDays) {
                        if (completionDay.dayOfWeek == event.dayOfWeek) {
                            completionDays.add(completionDay.copy(isHighlighted = !completionDay.isHighlighted))
                        } else {
                            completionDays.add(completionDay)
                        }
                    }
                    _bottomSheetState.value = it.copy(completionDays = completionDays)
                }
            }
            is BottomSheetEvent.Close -> {
                _bottomSheetState.value = BottomSheetState.Closed
            }
            is BottomSheetEvent.Save -> {

            }
        }
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
    data class Edit(
        val id: Int? = null,
        val name: String = "",
        val description: String = "",
        val imageId: Int = R.drawable.ic_empty,
        val completionDays: List<CompletionDay>
    ): BottomSheetState()
}

sealed class BottomSheetEvent {
    data class NameChanged(val name: String): BottomSheetEvent()
    data class DescriptionChanged(val description: String): BottomSheetEvent()
    data class IconChanged(val imageId: Int): BottomSheetEvent()
    data class DayChanged(val dayOfWeek: DayOfWeek): BottomSheetEvent()
    object Close: BottomSheetEvent()
    object Save: BottomSheetEvent()
}