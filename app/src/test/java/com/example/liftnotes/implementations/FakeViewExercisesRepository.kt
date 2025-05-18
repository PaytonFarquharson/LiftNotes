package com.example.liftnotes.implementations

import com.example.liftnotes.database.model.Exercise
import com.example.liftnotes.repository.model.DataResult

class FakeViewExercisesRepository(private val result: DataResult<List<Exercise>>):
    ViewExercisesRepository {
    override suspend fun getCurrentExercises(sessionId: Int): DataResult<List<Exercise>> {
        return result
    }
}