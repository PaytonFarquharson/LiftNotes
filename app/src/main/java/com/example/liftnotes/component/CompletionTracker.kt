package com.example.liftnotes.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.liftnotes.repository.model.CompletionDay
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CompletionTracker(
    completionDays: List<CompletionDay>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(completionDays) {
            TrackerDay(it)
        }
    }
}

@Composable
private fun TrackerDay(
    completionDay: CompletionDay
) {
    val backgroundColor =
        if (completionDay.isHighlighted)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.surfaceVariant
    val textColor =
        if (completionDay.isHighlighted)
            MaterialTheme.colorScheme.onPrimary
        else
            MaterialTheme.colorScheme.onSurfaceVariant
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(32.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(backgroundColor)
                .fillMaxSize()
        )
        Text(
            text = completionDay.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            color = textColor
        )
    }
}