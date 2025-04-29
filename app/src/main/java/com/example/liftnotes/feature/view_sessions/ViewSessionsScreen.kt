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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.liftnotes.model.CurrentSession
import com.example.liftnotes.ui.components.CardIcon
import com.example.liftnotes.ui.components.CardNameDescription
import com.example.liftnotes.ui.components.CompletionTracker
import com.example.liftnotes.ui.components.FloatingAddButton
import com.example.liftnotes.ui.components.ReorderHapticFeedbackType
import com.example.liftnotes.ui.components.ReorderableCard
import com.example.liftnotes.ui.components.rememberReorderHapticFeedback
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewSessionsScreen(
    onSessionClick: (Int) -> Unit,
    viewModel: ViewSessionsViewModel = hiltViewModel()
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
            FloatingAddButton(onClick = { viewModel.onAddClick() })
        },
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.exclude(NavigationBarDefaults.windowInsets)
    ) { innerPadding ->
        Box {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val bottomSheetState by viewModel.bottomSheetState.collectAsStateWithLifecycle()

            when (val state = uiState) {
                UiState.Loading -> Loading()
                is UiState.Success -> Success(
                    onSessionClick,
                    { viewModel.onCurrentSessionsReorder(it) },
                    state.sessions,
                    innerPadding
                )
            }
            EditSessionBottomSheet(
                bottomSheetState = bottomSheetState,
                onBottomSheetEvent = { event -> viewModel.onBottomSheetEvent(event) },
            )
        }
    }
}

@Composable
private fun Loading() {

}

@Composable
private fun Success(
    onSessionClick: (Int) -> Unit,
    onSessionsReorder: (List<CurrentSession>) -> Unit,
    list: List<CurrentSession>,
    innerPadding: PaddingValues
) {
    val haptic = rememberReorderHapticFeedback()
    val lazyListState = rememberLazyListState()
    val reorderableLazyListState = rememberReorderableLazyListState(lazyListState) { from, to ->
        val reorderedList = list.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
        onSessionsReorder(reorderedList)
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
                ReorderableCard({ onSessionClick(currentSession.session.id) }) {
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