package com.example.liftnotes.ui.screens.view_sessions

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
import com.example.liftnotes.ui.common.DaySelector
import com.example.liftnotes.ui.common.FormButtons
import com.example.liftnotes.ui.common.IconPicker
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditSessionBottomSheet(
    bottomSheetState: BottomSheetState,
    onBottomSheetEvent: (BottomSheetEvent) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    if (bottomSheetState is BottomSheetState.Edit) {
        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest = { onBottomSheetEvent(BottomSheetEvent.Close) },
            sheetState = sheetState
        ) {
            Column {
                OutlinedTextField(
                    value = bottomSheetState.name,
                    onValueChange = { onBottomSheetEvent(BottomSheetEvent.NameChanged(it)) },
                    label = { Text(text = "Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                OutlinedTextField(
                    value = bottomSheetState.description,
                    onValueChange = { onBottomSheetEvent(BottomSheetEvent.DescriptionChanged(it)) },
                    label = { Text(text = "Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                IconPicker(
                    onIconSelected = { imageId -> onBottomSheetEvent(BottomSheetEvent.IconChanged(imageId))},
                    bottomSheetState.imageId
                )

                DaySelector(
                    completionDays = bottomSheetState.completionDays,
                    onClick = { dayOfWeek -> onBottomSheetEvent(BottomSheetEvent.DayChanged(dayOfWeek)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                FormButtons(
                    {
                        coroutineScope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            onBottomSheetEvent(BottomSheetEvent.Close)
                        }
                    },
                    { onBottomSheetEvent(BottomSheetEvent.Save) }
                )
            }
        }
    }
}