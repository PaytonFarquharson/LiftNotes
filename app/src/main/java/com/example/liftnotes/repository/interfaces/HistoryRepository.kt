package com.example.liftnotes.repository.interfaces

import com.example.liftnotes.database.model.HistoricalSession
import com.example.liftnotes.repository.model.DataResult
import com.example.liftnotes.repository.model.ViewHistoricalExercisesScreenData
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun getHistoricalSessions(): Flow<DataResult<List<HistoricalSession>>>
    fun getHistoricalSessionExercises(historicalSessionId: Int): Flow<DataResult<ViewHistoricalExercisesScreenData>>
}