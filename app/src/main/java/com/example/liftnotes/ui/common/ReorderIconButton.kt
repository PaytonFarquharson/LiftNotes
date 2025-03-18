package com.example.liftnotes.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DragHandle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ReorderIconButton(modifier: Modifier) {
    IconButton(
        modifier = modifier,
        onClick = {},
    ) {
        Icon(
            Icons.Rounded.DragHandle,
            contentDescription = "Reorder",
            tint = MaterialTheme.colorScheme.onSurface,
            //ripple()
        )
    }
}