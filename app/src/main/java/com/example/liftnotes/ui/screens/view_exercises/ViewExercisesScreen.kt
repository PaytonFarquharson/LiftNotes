package com.example.liftnotes.ui.screens.view_exercises

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.DragHandle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.liftnotes.model.Exercise
import com.example.liftnotes.ui.common.CardIcon
import com.example.liftnotes.ui.common.CardNameDescription
import com.example.liftnotes.ui.common.FloatingAddButton
import com.example.liftnotes.ui.common.ReorderHapticFeedbackType
import com.example.liftnotes.ui.common.ReorderIconButton
import com.example.liftnotes.ui.common.ReorderableCard
import com.example.liftnotes.ui.common.SetsRepsTime
import com.example.liftnotes.ui.common.rememberReorderHapticFeedback
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewExercisesScreen(
    sessionId: String,
    onExerciseClick: (String) -> Unit,
    navigateBack: () -> Unit,
    viewModel: ViewExercisesViewModel = viewModel(factory = ViewExercisesViewModel.provideFactory(sessionId))
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                title = {
                    Text("Exercises")
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            contentDescription = "Back",
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingAddButton(onClick = {})
        },
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.exclude(NavigationBarDefaults.windowInsets)
    ) { innerPadding ->
        val list by viewModel.currentExercises.collectAsStateWithLifecycle()
        Content(onExerciseClick, { viewModel.onCurrentExercisesReorder(it) }, list, innerPadding)
    }
}

@Composable
private fun Content(
    onExerciseClick: (String) -> Unit,
    onExercisesReorder: (List<Exercise>) -> Unit,
    list: List<Exercise>,
    innerPadding: PaddingValues
) {
    val haptic = rememberReorderHapticFeedback()
    val lazyListState = rememberLazyListState()
    val reorderableLazyListState = rememberReorderableLazyListState(lazyListState) { from, to ->
        val reorderedList = list.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
        onExercisesReorder(reorderedList)
        haptic.performHapticFeedback(ReorderHapticFeedbackType.MOVE)
    }

    LazyColumn(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
        state = lazyListState,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(list, key = { _, exercise -> exercise.id }) { _, exercise ->
            ReorderableItem(reorderableLazyListState, key = exercise.id) { isDragging ->
                ReorderableCard({ onExerciseClick(exercise.id) }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                    ) {
                        CardIcon(exercise.imageId)
                        CardNameDescription(
                            exercise.name,
                            exercise.description,
                            Modifier.weight(0.6f)
                        )
                        SetsRepsTime(
                            exercise.sets,
                            exercise.reps,
                            exercise.time,
                            Modifier.weight(0.4f)
                        )
                        ReorderIconButton(
                            modifier = Modifier
                                .draggableHandle(
                                    onDragStarted = { haptic.performHapticFeedback(ReorderHapticFeedbackType.START) },
                                    onDragStopped = { haptic.performHapticFeedback(ReorderHapticFeedbackType.END) },
                                )
                        )
                    }
                }
            }
        }
    }
}