package com.example.liftnotes.repository.implementations

import com.example.liftnotes.database.model.Exercise
import com.example.liftnotes.database.LiftNotesDao
import com.example.liftnotes.repository.model.DataResult
import com.example.liftnotes.database.model.Session
import com.example.liftnotes.repository.interfaces.WorkoutRepository
import com.example.liftnotes.repository.model.ViewExercisesScreenData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class WorkoutRepositoryImpl @Inject constructor(
    private val dao: LiftNotesDao
) : WorkoutRepository {

    override fun getSessions(): Flow<DataResult<List<Session>>> =
        dao.getSessions()
            .map { sessions ->
                DataResult.Success(sessions) as DataResult<List<Session>>
            }
            .onStart { emit(DataResult.Loading) }
            .catch { e -> emit(DataResult.Error(e.message)) }

    override fun getCurrentSessions(): Flow<DataResult<List<Session>>> =
        combine(
            dao.getCurrentSessionIds(),
            dao.getSessions()
        ) { currentSessionIds, sessions ->
            currentSessionIds?.let {
                val mappedSessions = sessions.associateBy { it.id }
                val currentSessions = currentSessionIds.currentSessionIdList.mapNotNull { sessionId ->
                    mappedSessions[sessionId]
                }
                DataResult.Success(currentSessions)
            } ?: DataResult.Error("Current sessions not found")
        }
            .onStart { emit(DataResult.Loading) }
            .catch { e -> emit(DataResult.Error(e.message)) }

    override fun getExercises(): Flow<DataResult<List<Exercise>>> =
        dao.getExercises()
            .map { exercises ->
                DataResult.Success(exercises) as DataResult<List<Exercise>>
            }
            .onStart { emit(DataResult.Loading) }
            .catch { e -> emit(DataResult.Error(e.message)) }

    override fun getSessionExercises(sessionId: Int): Flow<DataResult<ViewExercisesScreenData>> =
        combine(
            dao.getSession(sessionId),
            dao.getExercises()
        ) { session, exercises ->
            session?.let {
                val mappedExercises = exercises.associateBy { it.id }
                val currentExercises = session.exerciseIds.mapNotNull { exerciseId ->
                    mappedExercises[exerciseId]
                }
                DataResult.Success(ViewExercisesScreenData(session, currentExercises))
            } ?: DataResult.Error("Session not found")
        }
            .onStart { emit(DataResult.Loading) }
            .catch { e -> emit(DataResult.Error(e.message)) }
}