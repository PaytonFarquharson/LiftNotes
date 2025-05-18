package com.example.liftnotes.repository.interfaces

import com.example.liftnotes.database.model.Exercise
import com.example.liftnotes.repository.model.DataResult
import com.example.liftnotes.database.model.Session
import com.example.liftnotes.repository.model.ViewExercisesScreenData
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    fun getSessions(): Flow<DataResult<List<Session>>>
    fun getCurrentSessions(): Flow<DataResult<List<Session>>>
    fun getExercises(): Flow<DataResult<List<Exercise>>>
    fun getSessionExercises(sessionId: Int): Flow<DataResult<ViewExercisesScreenData>>
}