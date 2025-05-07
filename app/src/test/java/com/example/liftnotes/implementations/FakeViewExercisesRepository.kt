package com.example.liftnotes.implementations

import com.example.liftnotes.interfaces.ViewExercisesRepository
import com.example.liftnotes.model.Exercise
import com.example.liftnotes.model.ResultOf
import com.example.liftnotes.test.testExercisesModel

class FakeViewExercisesRepository: ViewExercisesRepository {
    override suspend fun getCurrentExercises(sessionId: Int): ResultOf<List<Exercise>> {
        return ResultOf.Success(testExercisesModel)
    }
}