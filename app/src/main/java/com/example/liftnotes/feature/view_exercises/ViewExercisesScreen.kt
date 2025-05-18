package com.example.liftnotes.feature.view_exercises

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.liftnotes.database.model.Exercise
import com.example.liftnotes.test.testExercisesModel
import com.example.liftnotes.component.CardIcon
import com.example.liftnotes.component.CardNameDescription
import com.example.liftnotes.component.ExerciseValues
import com.example.liftnotes.component.FloatingAddButton
import com.example.liftnotes.component.ReorderHapticFeedbackType
import com.example.liftnotes.component.ReorderableCard
import com.example.liftnotes.component.rememberReorderHapticFeedback
import com.example.liftnotes.repository.model.ViewExercisesScreenData
import com.example.liftnotes.theme.LiftNotesTheme
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewExercisesScreen(
    uiState: Result<ViewExercisesScreenData>,
    onEvent: (ViewExercisesUiEvent) -> Unit,
    bottomSheetState: EditExerciseBottomSheetState,
    onBottomSheetEvent: (EditExerciseBottomSheetEvent) -> Unit
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
                    IconButton(onClick = { onEvent(ViewExercisesUiEvent.BackPressed) }) {
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
        when (uiState) {
            ViewExercisesUiState.Loading -> Loading(innerPadding)
            is ViewExercisesUiState.Success -> Success(
                onEvent,
                uiState.exercises,
                innerPadding
            )
            is ViewExercisesUiState.Error -> Error(innerPadding)
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
    onEvent: (ViewExercisesUiEvent) -> Unit,
    list: List<Exercise>,
    innerPadding: PaddingValues
) {
    val haptic = rememberReorderHapticFeedback()
    val lazyListState = rememberLazyListState()
    val reorderableLazyListState = rememberReorderableLazyListState(lazyListState) { from, to ->
        val reorderedList = list.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
        onEvent(ViewExercisesUiEvent.CurrentExercisesReordered(reorderedList))
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
                ReorderableCard({ /*TODO: onClick*/ }) {
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
                        CardIcon(exercise.imageId)
                        CardNameDescription(
                            exercise.name,
                            exercise.description,
                            Modifier.weight(0.5f)
                        )
                        ExerciseValues(
                            exercise,
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

@Preview
@Composable
fun ViewExercisesScreenPreview() {
    LiftNotesTheme {
        ViewExercisesScreen(
            uiState = ViewExercisesUiState.Success(testExercisesModel),
            onEvent = {},
            bottomSheetState = EditExerciseBottomSheetState.Closed,
            onBottomSheetEvent = {}
        )
    }
}