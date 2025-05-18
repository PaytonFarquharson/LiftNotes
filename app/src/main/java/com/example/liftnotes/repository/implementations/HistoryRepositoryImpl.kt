package com.example.liftnotes.repository.implementations

import com.example.liftnotes.database.model.HistoricalSession
import com.example.liftnotes.database.LiftNotesDao
import com.example.liftnotes.repository.model.DataResult
import com.example.liftnotes.repository.interfaces.HistoryRepository
import com.example.liftnotes.repository.model.ViewHistoricalExercisesScreenData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val dao: LiftNotesDao
) : HistoryRepository {

    override fun getHistoricalSessions(): Flow<DataResult<List<HistoricalSession>>> =
        dao.getHistoricalSessions()
            .map { historicalSessions ->
                DataResult.Success(historicalSessions) as DataResult<List<HistoricalSession>>
            }
            .onStart { emit(DataResult.Loading) }
            .catch { e -> emit(DataResult.Error(e.message)) }

    override fun getHistoricalSessionExercises(historicalSessionId: Int): Flow<DataResult<ViewHistoricalExercisesScreenData>> =
        combine(
            dao.getHistoricalSession(historicalSessionId),
            dao.getHistoricalExercises()
        ) { historicalSession, historicalExercises ->
            historicalSession?.let {
                val exerciseMap = historicalExercises.associateBy { it.id }
                val exercises = historicalSession.historicalExerciseIds.mapNotNull { id ->
                    exerciseMap[id]
                }
                DataResult.Success(ViewHistoricalExercisesScreenData(historicalSession, exercises))
            } ?: DataResult.Error("Historical session not found")
        }
            .onStart { emit(DataResult.Loading) }
            .catch { e -> emit(DataResult.Error(e.message)) }
}