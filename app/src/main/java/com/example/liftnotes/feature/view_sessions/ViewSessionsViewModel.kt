package com.example.liftnotes.feature.view_sessions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liftnotes.R
import com.example.liftnotes.interfaces.ViewSessionsRepository
import com.example.liftnotes.model.CompletionDay
import com.example.liftnotes.model.CurrentSession
import com.example.liftnotes.model.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import javax.inject.Inject

@HiltViewModel
class ViewSessionsViewModel @Inject constructor(
    private val repository: ViewSessionsRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _bottomSheetState: MutableStateFlow<BottomSheetState> =
        MutableStateFlow(BottomSheetState.Closed)
    val bottomSheetState = _bottomSheetState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = UiState.Success(repository.getCurrentSessions())
        }
    }

    fun onCurrentSessionsReorder(currentSessions: List<CurrentSession>) {
        _uiState.value = UiState.Success(currentSessions)
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
}

sealed class UiState {
    object Loading: UiState()
    data class Success(val sessions: List<CurrentSession>): UiState()
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