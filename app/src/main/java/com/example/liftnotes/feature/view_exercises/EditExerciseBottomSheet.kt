package com.example.liftnotes.feature.view_exercises

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.liftnotes.component.FormButtons
import com.example.liftnotes.component.IconPicker
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditExerciseBottomSheet(
    bottomSheetState: EditExerciseBottomSheetState,
    onBottomSheetEvent: (EditExerciseBottomSheetEvent) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    if (bottomSheetState is EditExerciseBottomSheetState.Edit) {
        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest = { onBottomSheetEvent(EditExerciseBottomSheetEvent.Close) },
            sheetState = sheetState
        ) {
            Column {
                OutlinedTextField(
                    value = bottomSheetState.name,
                    onValueChange = { onBottomSheetEvent(EditExerciseBottomSheetEvent.NameChanged(it)) },
                    label = { Text(text = "Name") },
                    isError = bottomSheetState.nameError != null,
                    supportingText = {
                        bottomSheetState.nameError?.let {
                            Text(text = bottomSheetState.nameError, color = MaterialTheme.colorScheme.error)
                        }
                    },
                    trailingIcon = {
                        if (bottomSheetState.nameError != null) {
                            Icon(
                                imageVector = Icons.Default.Error,
                                contentDescription = "Error",
                                tint = MaterialTheme.colorScheme.error
                            )
                        } else if (bottomSheetState.name.isNotEmpty()) {
                            IconButton(
                                onClick = { onBottomSheetEvent(EditExerciseBottomSheetEvent.NameChanged("")) }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Cancel,
                                    contentDescription = "Clear text"
                                )
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                OutlinedTextField(
                    value = bottomSheetState.description,
                    onValueChange = { onBottomSheetEvent(EditExerciseBottomSheetEvent.DescriptionChanged(it)) },
                    label = { Text(text = "Description") },
                    trailingIcon = {
                       if (bottomSheetState.description.isNotEmpty()) {
                           IconButton(
                               onClick = { onBottomSheetEvent(EditExerciseBottomSheetEvent.DescriptionChanged("")) }
                           ) {
                               Icon(
                                   imageVector = Icons.Default.Cancel,
                                   contentDescription = "Clear text"
                               )
                           }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                IconPicker(
                    onIconSelected = { imageId ->
                        onBottomSheetEvent(
                            EditExerciseBottomSheetEvent.IconChanged(
                                imageId
                            )
                        )
                    },
                    bottomSheetState.imageId
                )

                Row {
                    OutlinedTextField(
                        value = bottomSheetState.weight?.toString() ?: "",
                        onValueChange = { onBottomSheetEvent(EditExerciseBottomSheetEvent.WeightChanged(it.toFloatOrNull())) },
                        label = { Text(text = "Weight") },
                        modifier = Modifier
                            .padding(8.dp)
                    )

                    OutlinedTextField(
                        value = bottomSheetState.sets?.toString() ?: "",
                        onValueChange = { onBottomSheetEvent(EditExerciseBottomSheetEvent.SetsChanged(it.toIntOrNull())) },
                        label = { Text(text = "Sets") },
                        modifier = Modifier
                            .padding(8.dp)
                    )

                    OutlinedTextField(
                        value = bottomSheetState.reps?.min.toString(),
                        onValueChange = { onBottomSheetEvent(EditExerciseBottomSheetEvent.MinRepsChanged(it.toIntOrNull())) },
                        label = { Text(text = "Min Reps") },
                        modifier = Modifier
                            .padding(8.dp)
                    )

                    OutlinedTextField(
                        value = bottomSheetState.reps?.max.toString(),
                        onValueChange = { onBottomSheetEvent(EditExerciseBottomSheetEvent.MaxRepsChanged(it.toIntOrNull())) },
                        label = { Text(text = "Max Reps") },
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }

                // Time field
                OutlinedTextField(
                    value = bottomSheetState.time?.toString() ?: "",
                    onValueChange = { onBottomSheetEvent(EditExerciseBottomSheetEvent.TimeChanged(it.toIntOrNull())) },
                    label = { Text(text = "Time (seconds)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                FormButtons(
                    {
                        coroutineScope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            onBottomSheetEvent(EditExerciseBottomSheetEvent.Close)
                        }
                    },
                    { onBottomSheetEvent(EditExerciseBottomSheetEvent.Save) }
                )
            }
        }
    }
}

