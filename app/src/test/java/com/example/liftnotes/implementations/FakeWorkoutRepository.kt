package com.example.liftnotes.implementations

import com.example.liftnotes.database.model.Exercise
import com.example.liftnotes.database.model.Session
import com.example.liftnotes.repository.interfaces.WorkoutRepository
import com.example.liftnotes.repository.model.CurrentSession
import com.example.liftnotes.repository.model.DataResult
import com.example.liftnotes.repository.model.ViewExercisesScreenData
import kotlinx.coroutines.flow.Flow

class FakeWorkoutRepository: WorkoutRepository {
    override fun getSessions(): Flow<DataResult<List<Session>>> {
        TODO("Not yet implemented")
    }

    override fun getCurrentSessions(): Flow<DataResult<List<CurrentSession>>> {
        TODO("Not yet implemented")
    }

    override fun getExercises(): Flow<DataResult<List<Exercise>>> {
        TODO("Not yet implemented")
    }

    override fun getSessionExercises(sessionId: Int): Flow<DataResult<ViewExercisesScreenData>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateSession(session: Session): Int {
        TODO("Not yet implemented")
    }

    override suspend fun updateCurrentSessionIds(currentSessionIds: List<Int>) {
        TODO("Not yet implemented")
    }

    override suspend fun updateExercise(exercise: Exercise): Int {
        TODO("Not yet implemented")
    }
}