package com.example.liftnotes.feature.view_sessions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.liftnotes.ui.components.DaySelector
import com.example.liftnotes.ui.components.FormButtons
import com.example.liftnotes.ui.components.IconPicker
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditSessionBottomSheet(
    bottomSheetState: EditSessionBottomSheetState,
    onBottomSheetEvent: (EditSessionBottomSheetEvent) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    if (bottomSheetState is EditSessionBottomSheetState.Edit) {
        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest = { onBottomSheetEvent(EditSessionBottomSheetEvent.Close) },
            sheetState = sheetState
        ) {
            Column {
                OutlinedTextField(
                    value = bottomSheetState.name,
                    onValueChange = { onBottomSheetEvent(EditSessionBottomSheetEvent.NameChanged(it)) },
                    label = { Text(text = "Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                OutlinedTextField(
                    value = bottomSheetState.description,
                    onValueChange = { onBottomSheetEvent(EditSessionBottomSheetEvent.DescriptionChanged(it)) },
                    label = { Text(text = "Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                IconPicker(
                    onIconSelected = { imageId ->
                        onBottomSheetEvent(
                            EditSessionBottomSheetEvent.IconChanged(
                                imageId
                            )
                        )
                    },
                    bottomSheetState.imageId
                )

                DaySelector(
                    completionDays = bottomSheetState.completionDays,
                    onClick = { dayOfWeek ->
                        onBottomSheetEvent(
                            EditSessionBottomSheetEvent.DayChanged(
                                dayOfWeek
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )

                FormButtons(
                    {
                        coroutineScope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            onBottomSheetEvent(EditSessionBottomSheetEvent.Close)
                        }
                    },
                    { onBottomSheetEvent(EditSessionBottomSheetEvent.Save) }
                )
            }
        }
    }
}