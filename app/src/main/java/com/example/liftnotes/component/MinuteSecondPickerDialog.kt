package com.example.liftnotes.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.w2sv.wheelpicker.WheelPicker
import com.w2sv.wheelpicker.rememberWheelPickerState

@Composable
fun MinuteSecondPickerDialog(
    initialTime: Int = 0,
    onConfirm: (seconds: Int) -> Unit,
    onDismiss: () -> Unit
) {
    val hoursState = rememberWheelPickerState(
        itemCount = 25,
        startIndex = initialTime / 3600
    )
    val minutesState = rememberWheelPickerState(
        itemCount = 60,
        startIndex = (initialTime % 3600) / 60
    )
    val secondsState = rememberWheelPickerState(
        itemCount = 60,
        startIndex = initialTime % 60
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.background,
        title = { Text("Set Time") },
        text = {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                // Hours Picker
                WheelPicker(
                    modifier = Modifier.weight(1f),
                    state = hoursState,
                    focusBoxOverlay = {
                        FocusBoxOverlay(it)
                    }
                ) { index ->
                    Text(text = "$index hour")
                }

                // Minutes Picker
                WheelPicker(
                    modifier = Modifier.weight(1f),
                    state = minutesState,
                    focusBoxOverlay = {
                        FocusBoxOverlay(it)
                    }
                ) { index ->
                    Text(text = "$index min")
                }

                // Seconds Picker
                WheelPicker(
                    modifier = Modifier.weight(1f),
                    state = secondsState,
                    focusBoxOverlay = {
                        FocusBoxOverlay(it)
                    }
                ) { index ->
                    Text(text = "$index sec")
                }
            }
        },
        confirmButton = {
            TextButton (onClick = { onConfirm(hoursState.snappedIndex*3600+minutesState.snappedIndex*60+secondsState.snappedIndex) }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
private fun FocusBoxOverlay(modifier: Modifier = Modifier) {
    val borderColor = MaterialTheme.colorScheme.primary
    Box(
        modifier = modifier
            .fillMaxSize()
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                // Draw top line
                drawLine(
                    color = borderColor,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = strokeWidth
                )
                // Draw bottom line
                drawLine(
                    color = borderColor,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = strokeWidth
                )
            }
    )
}
