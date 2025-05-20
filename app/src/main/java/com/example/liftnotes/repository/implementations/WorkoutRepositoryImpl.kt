package com.example.liftnotes.repository.implementations

import com.example.liftnotes.database.model.Exercise
import com.example.liftnotes.database.LiftNotesDao
import com.example.liftnotes.database.model.CurrentSessionIds
import com.example.liftnotes.database.model.HistoricalSession
import com.example.liftnotes.repository.model.DataResult
import com.example.liftnotes.database.model.Session
import com.example.liftnotes.repository.interfaces.WorkoutRepository
import com.example.liftnotes.repository.model.CompletionDay
import com.example.liftnotes.repository.model.CurrentSession
import com.example.liftnotes.repository.model.ViewExercisesScreenData
import com.example.liftnotes.utils.DateUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.time.DayOfWeek
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

    override fun getCurrentSessions(): Flow<DataResult<List<CurrentSession>>> =
        combine(
            dao.getCurrentSessionIds(),
            dao.getSessions(),
            dao.getHistoricalSessions()
        ) { currentSessionIds, sessions, historicalSessions ->
            currentSessionIds?.let {
                val mappedSessions = sessions.associateBy { it.id }
                val currentSessions = currentSessionIds.currentSessionIdList.mapNotNull { sessionId ->
                    mappedSessions[sessionId]?.let { session ->
                        CurrentSession(
                            session = session,
                            completionDays = getCompletionDays(session, historicalSessions)
                        )
                    }
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

    override suspend fun updateSession(session: Session): Int = dao.upsertSession(session).toInt()

    override suspend fun updateCurrentSessionIds(currentSessionIds: List<Int>) {
        dao.upsertCurrentSessions(CurrentSessionIds(currentSessionIdList = currentSessionIds))
    }

    override suspend fun updateExercise(exercise: Exercise): Int = dao.upsertExercise(exercise).toInt()

    private fun getCompletionDays(session: Session, historicalSessions: List<HistoricalSession>): List<CompletionDay> {
        val daysHistory = historicalSessions
            .filter { it.id == session.id && it.dateTime.isAfter(DateUtils.getStartOfWeek(DayOfWeek.MONDAY)) }
            .map { it.dateTime.dayOfWeek }

        var flexDays = daysHistory
            .groupingBy { it }
            .eachCount()
            .entries
            .sumOf { (day, count) ->
                if (day in session.daysOfWeek) (count - 1).coerceAtLeast(0)
                else count
            }

        val completionsDays = session.daysOfWeek.map { dayOfWeek ->
            val isMatched = dayOfWeek in daysHistory
            val isFlexUsed = !isMatched && flexDays > 0
            if (isFlexUsed) flexDays--

            CompletionDay(
                dayOfWeek = dayOfWeek,
                isHighlighted = isMatched || isFlexUsed
            )
        }

        return completionsDays
    }
}