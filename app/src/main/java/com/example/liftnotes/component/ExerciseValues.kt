package com.example.liftnotes.component

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
import com.example.liftnotes.database.model.Exercise
import com.example.liftnotes.database.model.getSetsString
import com.example.liftnotes.database.model.getTimeString
import com.example.liftnotes.database.model.getWeightString

@Composable
fun ExerciseValues(exercise: Exercise, modifier: Modifier) {
    val values = listOfNotNull(
        getWeightString(exercise.weight).takeIf { it.isNotEmpty() }?.let { R.drawable.ic_weight to it },
        getSetsString(exercise.sets, exercise.reps).takeIf { it.isNotEmpty() }?.let { R.drawable.ic_repetition to it },
        getTimeString(exercise.time).takeIf { it.isNotEmpty() }?.let { R.drawable.ic_timer to it }
    )

    Row(modifier = modifier) {
        values.forEach { (icon, value) ->
            ExerciseValue(icon, value, Modifier.weight(1f))
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