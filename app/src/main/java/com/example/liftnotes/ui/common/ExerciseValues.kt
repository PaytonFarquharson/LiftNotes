package com.example.liftnotes.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.liftnotes.R
import com.example.liftnotes.model.Exercise

@Composable
fun ExerciseValues(exercise: Exercise, modifier: Modifier) {
    Row(modifier = modifier) {
        exercise.getWeightString().takeIf { it.isNotEmpty() }?.let {
            ExerciseValue(R.drawable.ic_weight, it, Modifier.weight(1f))
        }
        exercise.getSetsString().takeIf { it.isNotEmpty() }?.let {
            ExerciseValue(R.drawable.ic_repetition, it, Modifier.weight(1f))
        }
        exercise.getTimeString().takeIf { it.isNotEmpty() }?.let {
            ExerciseValue(R.drawable.ic_timer, it, Modifier.weight(1f))
        }
    }
}

@Composable
fun ExerciseValue(@DrawableRes imageId: Int, value: String, modifier: Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(imageId),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .size(24.dp)
                .padding(bottom = 4.dp)
        )
        Text(
            text = value,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleSmall
        )
    }
}