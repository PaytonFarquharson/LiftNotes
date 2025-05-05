package com.example.liftnotes.feature.view_sessions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liftnotes.R
import com.example.liftnotes.interfaces.ViewSessionsRepository
import com.example.liftnotes.model.CompletionDay
import com.example.liftnotes.model.CurrentSession
import com.example.liftnotes.model.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import javax.inject.Inject

@HiltViewModel
class ViewSessionsViewModel @Inject constructor(
    private val repository: ViewSessionsRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<ViewSessionsUiState> =
        MutableStateFlow(ViewSessionsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<ViewSessionsUiEffect>()
    val effect = _effect.asSharedFlow()

    private val _bottomSheetState: MutableStateFlow<EditSessionBottomSheetState> =
        MutableStateFlow(EditSessionBottomSheetState.Closed)
    val bottomSheetState = _bottomSheetState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = ViewSessionsUiState.Success(repository.getCurrentSessions())
        }
    }

    fun onUiEvent(event: ViewSessionsUiEvent) {
        when (event) {
            is ViewSessionsUiEvent.SessionClicked -> {
                viewModelScope.launch { _effect.emit(ViewSessionsUiEffect.NavigateToSession(event.sessionId)) }
            }

            is ViewSessionsUiEvent.CurrentSessionsReordered -> {
                _uiState.value = ViewSessionsUiState.Success(event.currentSessions)
            }

            is ViewSessionsUiEvent.AddClicked -> {
                _bottomSheetState.value =
                    EditSessionBottomSheetState.Edit(completionDays = getCompletionDays())
            }

            is ViewSessionsUiEvent.EditClicked -> {
                _bottomSheetState.value = EditSessionBottomSheetState.Edit(
                    event.session.id,
                    event.session.name,
                    event.session.description.orEmpty(),
                    event.session.imageId,
                    getCompletionDays(event.session.daysOfWeek)
                )
            }

            is ViewSessionsUiEvent.DeleteClicked -> {
                TODO()
            }
        }
    }

    private fun getCompletionDays(daysOfWeek: List<DayOfWeek> = emptyList()): List<CompletionDay> {
        val completionsDays = mutableListOf<CompletionDay>()
        for (day in DayOfWeek.entries) {
            completionsDays.add(CompletionDay(day, daysOfWeek.contains(day)))
        }
        return completionsDays
    }

    fun onBottomSheetEvent(event: EditSessionBottomSheetEvent) {
        when (event) {
            is EditSessionBottomSheetEvent.NameChanged -> {
                (_bottomSheetState.value as? EditSessionBottomSheetState.Edit)?.let {
                    _bottomSheetState.value = it.copy(name = event.name)
                }
            }

            is EditSessionBottomSheetEvent.DescriptionChanged -> {
                (_bottomSheetState.value as? EditSessionBottomSheetState.Edit)?.let {
                    _bottomSheetState.value = it.copy(description = event.description)
                }
            }

            is EditSessionBottomSheetEvent.IconChanged -> {
                (_bottomSheetState.value as? EditSessionBottomSheetState.Edit)?.let {
                    _bottomSheetState.value = it.copy(imageId = event.imageId)
                }
            }

            is EditSessionBottomSheetEvent.DayChanged -> {
                (_bottomSheetState.value as? EditSessionBottomSheetState.Edit)?.let {
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

            is EditSessionBottomSheetEvent.Close -> {
                _bottomSheetState.value = EditSessionBottomSheetState.Closed
            }

            is EditSessionBottomSheetEvent.Save -> {

            }
        }
    }
}

sealed class ViewSessionsUiState {
    object Loading : ViewSessionsUiState()
    data class Success(val sessions: List<CurrentSession>) : ViewSessionsUiState()
}

sealed class ViewSessionsUiEffect {
    data class NavigateToSession(val sessionId: Int) : ViewSessionsUiEffect()
}

sealed class ViewSessionsUiEvent {
    data class SessionClicked(val sessionId: Int) : ViewSessionsUiEvent()
    data class CurrentSessionsReordered(val currentSessions: List<CurrentSession>) :
        ViewSessionsUiEvent()

    object AddClicked : ViewSessionsUiEvent()
    data class EditClicked(val session: Session) : ViewSessionsUiEvent()
    data class DeleteClicked(val session: Session) : ViewSessionsUiEvent()
}

sealed class EditSessionBottomSheetState {
    object Closed : EditSessionBottomSheetState()
    data class Edit(
        val id: Int? = null,
        val name: String = "",
        val description: String = "",
        val imageId: Int = R.drawable.ic_empty,
        val completionDays: List<CompletionDay>
    ) : EditSessionBottomSheetState()
}

sealed class EditSessionBottomSheetEvent {
    data class NameChanged(val name: String) : EditSessionBottomSheetEvent()
    data class DescriptionChanged(val description: String) : EditSessionBottomSheetEvent()
    data class IconChanged(val imageId: Int) : EditSessionBottomSheetEvent()
    data class DayChanged(val dayOfWeek: DayOfWeek) : EditSessionBottomSheetEvent()
    object Close : EditSessionBottomSheetEvent()
    object Save : EditSessionBottomSheetEvent()
}