package com.example.liftnotes.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FormButtons(
    onCloseClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    Row(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Spacer(Modifier.weight(1f))
        OutlinedButton(
            onClick = onCloseClick,
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(text = "Cancel")
        }
        Button(
            onClick = onSaveClick,
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(text = "Save")
        }
    }
}