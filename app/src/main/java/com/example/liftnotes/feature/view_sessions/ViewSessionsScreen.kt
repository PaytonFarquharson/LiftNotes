package com.example.liftnotes.feature.view_sessions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.liftnotes.model.CurrentSession
import com.example.liftnotes.test.testCurrentSessionsModel
import com.example.liftnotes.ui.components.CardIcon
import com.example.liftnotes.ui.components.CardNameDescription
import com.example.liftnotes.ui.components.CompletionTracker
import com.example.liftnotes.ui.components.FloatingAddButton
import com.example.liftnotes.ui.components.ReorderHapticFeedbackType
import com.example.liftnotes.ui.components.ReorderableCard
import com.example.liftnotes.ui.components.rememberReorderHapticFeedback
import com.example.liftnotes.ui.theme.LiftNotesTheme
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewSessionsScreen(
    uiState: ViewSessionsUiState,
    onEvent: (ViewSessionsUiEvent) -> Unit,
    bottomSheetState: EditSessionBottomSheetState,
    onBottomSheetEvent: (EditSessionBottomSheetEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                title = {
                    Text("Sessions")
                }
            )
        },
        floatingActionButton = {
            FloatingAddButton(onClick = { onEvent(ViewSessionsUiEvent.AddClicked) })
        },
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.exclude(NavigationBarDefaults.windowInsets)
    ) { innerPadding ->
        Box {
            when (uiState) {
                ViewSessionsUiState.Loading -> Loading(innerPadding)
                is ViewSessionsUiState.Success -> Success(
                    onEvent,
                    uiState.sessions,
                    innerPadding
                )
                is ViewSessionsUiState.Error -> Error(innerPadding)
            }
            EditSessionBottomSheet(
                bottomSheetState = bottomSheetState,
                onBottomSheetEvent = onBottomSheetEvent,
            )
        }
    }
}

@Composable
private fun Loading(innerPadding: PaddingValues) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun Error(innerPadding: PaddingValues) {

}

@Composable
private fun Success(
    onEvent: (ViewSessionsUiEvent) -> Unit,
    list: List<CurrentSession>,
    innerPadding: PaddingValues
) {
    val haptic = rememberReorderHapticFeedback()
    val lazyListState = rememberLazyListState()
    val reorderableLazyListState = rememberReorderableLazyListState(lazyListState) { from, to ->
        val reorderedList = list.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
        onEvent(ViewSessionsUiEvent.CurrentSessionsReordered(reorderedList))
        haptic.performHapticFeedback(ReorderHapticFeedbackType.MOVE)
    }

    LazyColumn(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
        state = lazyListState,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(
            list,
            key = { _, currentSession -> currentSession.session.id }) { _, currentSession ->
            ReorderableItem(
                reorderableLazyListState,
                key = currentSession.session.id
            ) { isDragging ->
                ReorderableCard({ onEvent(ViewSessionsUiEvent.SessionClicked(currentSession.session.id)) }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .draggableHandle(
                                onDragStarted = {
                                    haptic.performHapticFeedback(
                                        ReorderHapticFeedbackType.START
                                    )
                                },
                                onDragStopped = {
                                    haptic.performHapticFeedback(
                                        ReorderHapticFeedbackType.END
                                    )
                                },
                            )
                            .padding(vertical = 8.dp)
                    ) {
                        CardIcon(currentSession.session.imageId)
                        CardNameDescription(
                            currentSession.session.name,
                            currentSession.session.description,
                            Modifier.weight(0.5f)
                        )
                        CompletionTracker(
                            currentSession.completionDays,
                            Modifier
                                .weight(0.5f)
                                .padding(end = 8.dp)
                                .fillMaxHeight()
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ViewSessionsScreenPreview() {
    LiftNotesTheme {
        ViewSessionsScreen(
            uiState = ViewSessionsUiState.Success(testCurrentSessionsModel),
            onEvent = {},
            bottomSheetState = EditSessionBottomSheetState.Closed,
            onBottomSheetEvent = {}
        )
    }
}