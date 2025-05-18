package com.example.liftnotes.repository.model

import com.example.liftnotes.database.model.HistoricalExercise
import com.example.liftnotes.database.model.HistoricalSession

data class ViewHistoricalExercisesScreenData(
    val historicalSession: HistoricalSession,
    val historicalExercises: List<HistoricalExercise>
)