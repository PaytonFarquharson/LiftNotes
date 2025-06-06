package com.example.liftnotes.feature.view_exercises

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.liftnotes.component.ClickableTextField
import com.example.liftnotes.component.FormButtons
import com.example.liftnotes.component.IconPicker
import com.example.liftnotes.component.MinuteSecondPickerDialog
import com.example.liftnotes.component.NumberTextField
import com.example.liftnotes.database.model.getTimeString
import com.example.liftnotes.utils.StringUtils.trimUnnecessaryDecimals
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditExerciseBottomSheet(
    bottomSheetState: EditExerciseBottomSheetState,
    onBottomSheetEvent: (EditExerciseBottomSheetEvent) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    var timeDialogOpen by remember { mutableStateOf(false) }
    val weightIncrements = listOf(1.25f, 2.5f, 5f, 10f, 25f, 45f)
    var isDropDownExpanded by remember { mutableStateOf(false) }

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
                            Text(
                                text = bottomSheetState.nameError,
                                color = MaterialTheme.colorScheme.error
                            )
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
                                onClick = {
                                    onBottomSheetEvent(
                                        EditExerciseBottomSheetEvent.NameChanged(
                                            ""
                                        )
                                    )
                                }
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
                        .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 4.dp)
                )
                OutlinedTextField(
                    value = bottomSheetState.description,
                    onValueChange = {
                        onBottomSheetEvent(
                            EditExerciseBottomSheetEvent.DescriptionChanged(
                                it
                            )
                        )
                    },
                    label = { Text(text = "Description") },
                    trailingIcon = {
                        if (bottomSheetState.description.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    onBottomSheetEvent(
                                        EditExerciseBottomSheetEvent.DescriptionChanged(
                                            ""
                                        )
                                    )
                                }
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
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                ) {
                    var weightInput by remember {
                        mutableStateOf(
                            trimUnnecessaryDecimals(
                                bottomSheetState.weight?.toString() ?: ""
                            )
                        )
                    }
                    OutlinedTextField(
                        value = weightInput,
                        onValueChange = {
                            val regex = Regex("^\\d{0,4}(\\.\\d{0,2})?$")
                            if (it.isEmpty() || regex.matches(it)) {
                                weightInput = it
                                onBottomSheetEvent(
                                    EditExerciseBottomSheetEvent.WeightChanged(it.toFloatOrNull())
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = { Text(text = "Weight") },
                        suffix = {
                            Text(
                                text = "lbs",
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        },
                        trailingIcon = if (bottomSheetState.weight != null) {
                            {
                                IconButton(
                                    onClick = {
                                        weightInput = ""
                                        onBottomSheetEvent(
                                            EditExerciseBottomSheetEvent.WeightChanged(null)
                                        )
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Cancel,
                                        contentDescription = "Clear weight"
                                    )
                                }
                            }
                        } else {
                            null
                        },
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .weight(0.7f)
                            .onFocusChanged { weightInput = trimUnnecessaryDecimals(weightInput) },
                    )

                    val isEnabled = bottomSheetState.weight != null
                    ExposedDropdownMenuBox(
                        expanded = isDropDownExpanded,
                        onExpandedChange = {
                            isDropDownExpanded = !isDropDownExpanded && isEnabled
                        },
                        modifier = Modifier
                            .weight(0.3f)
                    ) {
                        val displayVal = if (bottomSheetState.weight == null) {
                            ""
                        } else {
                            bottomSheetState.weightIncrement?.toString() ?: ""
                        }
                        OutlinedTextField(
                            value = displayVal,
                            onValueChange = {},
                            label = { Text(text = "+/-") },
                            readOnly = true,
                            enabled = isEnabled,
                            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropDownExpanded) },
                        )
                        ExposedDropdownMenu(
                            expanded = isDropDownExpanded,
                            onDismissRequest = { isDropDownExpanded = false },
                            Modifier.background(MaterialTheme.colorScheme.background)
                        ) {
                            weightIncrements.forEach { increment ->
                                DropdownMenuItem(
                                    text = { Text(text = increment.toString()) },
                                    onClick = {
                                        onBottomSheetEvent(
                                            EditExerciseBottomSheetEvent.WeightIncrementChanged(
                                                increment
                                            )
                                        )
                                        isDropDownExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 12.dp, bottom = 4.dp)
                ) {
                    NumberTextField(
                        value = bottomSheetState.sets?.toString() ?: "",
                        label = "Sets",
                        onValueChangeEvent = { onBottomSheetEvent(EditExerciseBottomSheetEvent.SetsChanged(it.toIntOrNull())) },
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .weight(1f
                        )
                    )

                    ClickableTextField(
                        value = getTimeString(bottomSheetState.time),
                        label = "Time",
                        onClick = { timeDialogOpen = true },
                        modifier = Modifier
                            .weight(1f),

                        trailingIcon = {
                            if (bottomSheetState.time != null) {
                                IconButton(
                                    onClick = {
                                        onBottomSheetEvent(
                                            EditExerciseBottomSheetEvent.TimeChanged(
                                                null
                                            )
                                        )
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Cancel,
                                        contentDescription = "Clear time"
                                    )
                                }
                            }
                        }
                    )
                    if (timeDialogOpen) {
                        MinuteSecondPickerDialog(
                            initialTime = bottomSheetState.time ?: 0,
                            onConfirm = { seconds ->
                                onBottomSheetEvent(EditExerciseBottomSheetEvent.TimeChanged(seconds))
                                timeDialogOpen = false
                            },
                            onDismiss = { timeDialogOpen = false }
                        )
                    }
                }

                Text(
                    text = "Reps",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(end = 8.dp, top = 4.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp)
                ) {
                    NumberTextField(
                        value = bottomSheetState.reps?.min?.toString() ?: "",
                        label = "Min",
                        onValueChangeEvent = { onBottomSheetEvent(EditExerciseBottomSheetEvent.MinRepsChanged(it.toIntOrNull())) },
                        errorMessage = bottomSheetState.repsError,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .weight(1f)
                    )

                    NumberTextField(
                        value = bottomSheetState.reps?.max?.toString() ?: "",
                        label = "Max",
                        onValueChangeEvent = { onBottomSheetEvent(EditExerciseBottomSheetEvent.MaxRepsChanged(it.toIntOrNull())) },
                        enabled = (bottomSheetState.reps?.min ?: 0) > 0,
                        modifier = Modifier
                            .weight(1f)
                    )
                }

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

