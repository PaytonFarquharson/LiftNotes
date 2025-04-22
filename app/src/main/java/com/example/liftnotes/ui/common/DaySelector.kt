package com.example.liftnotes.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.liftnotes.model.CompletionDay
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DaySelector(
    completionDays: List<CompletionDay>,
    modifier: Modifier = Modifier,
    onClick: (DayOfWeek) -> Unit = {}
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        for (completionDay in completionDays) {
            TrackerDay(completionDay, onClick)
        }
    }
}

@Composable
private fun TrackerDay(
    completionDay: CompletionDay,
    onClick: (DayOfWeek) -> Unit
) {
    val selected = completionDay.isHighlighted
    FilterChip(
        onClick = { onClick(completionDay.dayOfWeek) },
        label = {
            Text(
                completionDay.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        selected = selected,
        leadingIcon = if (selected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Done icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        },
        modifier = Modifier
            .width(88.dp)
    )
}