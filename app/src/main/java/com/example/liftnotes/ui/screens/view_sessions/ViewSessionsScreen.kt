package com.example.liftnotes.ui.screens.view_sessions

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.exclude
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.liftnotes.model.CurrentSession
import com.example.liftnotes.ui.common.CardIcon
import com.example.liftnotes.ui.common.CardNameDescription
import com.example.liftnotes.ui.common.CompletionTracker
import com.example.liftnotes.ui.common.FloatingAddButton
import com.example.liftnotes.ui.common.ReorderHapticFeedbackType
import com.example.liftnotes.ui.common.ReorderableCard
import com.example.liftnotes.ui.common.rememberReorderHapticFeedback
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewSessionsScreen(
    onSessionClick: (Int) -> Unit,
    viewModel: ViewSessionsViewModel = viewModel(factory = ViewSessionsViewModel.provideFactory())
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
            FloatingAddButton(onClick = {})
        },
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.exclude(NavigationBarDefaults.windowInsets)
    ) { innerPadding ->
        val list by viewModel.currentSessions.collectAsStateWithLifecycle()
        Content(onSessionClick, { viewModel.onCurrentSessionsReorder(it) }, list, innerPadding)
    }
}

@Composable
private fun Content(
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
        itemsIndexed(list, key = { _, currentSession -> currentSession.session.id }) { _, currentSession ->
            ReorderableItem(reorderableLazyListState, key = currentSession.session.id) { isDragging ->
                ReorderableCard({ onSessionClick(currentSession.session.id) }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .draggableHandle(
                                onDragStarted = { haptic.performHapticFeedback(ReorderHapticFeedbackType.START) },
                                onDragStopped = { haptic.performHapticFeedback(ReorderHapticFeedbackType.END) },
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
                        )
                    }
                }
            }
        }
    }
}