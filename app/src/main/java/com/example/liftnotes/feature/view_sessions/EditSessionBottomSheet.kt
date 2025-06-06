package com.example.liftnotes.feature.view_sessions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.liftnotes.R
import com.example.liftnotes.component.DaySelector
import com.example.liftnotes.component.FormButtons
import com.example.liftnotes.component.IconPicker
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
                    isError = bottomSheetState.nameError != null,
                    supportingText = {
                        bottomSheetState.nameError?.let {
                            Text(text = bottomSheetState.nameError, color = MaterialTheme.colorScheme.error)
                        }
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_tag),
                            contentDescription = "Name Icon",
                            Modifier.size(24.dp)
                        )
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
                                onClick = { onBottomSheetEvent(EditSessionBottomSheetEvent.NameChanged("")) }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Cancel,
                                    contentDescription = "Clear text"
                                )
                            }
                        }
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 4.dp)
                )
                OutlinedTextField(
                    value = bottomSheetState.description,
                    onValueChange = { onBottomSheetEvent(EditSessionBottomSheetEvent.DescriptionChanged(it)) },
                    label = { Text(text = "Description") },
                    leadingIcon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_description),
                            contentDescription = "Description Icon",
                            Modifier.size(24.dp)
                        )
                    },
                    trailingIcon = {
                       if (bottomSheetState.description.isNotEmpty()) {
                           IconButton(
                               onClick = { onBottomSheetEvent(EditSessionBottomSheetEvent.DescriptionChanged("")) }
                           ) {
                               Icon(
                                   imageVector = Icons.Default.Cancel,
                                   contentDescription = "Clear text"
                               )
                           }
                        }
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
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
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
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

