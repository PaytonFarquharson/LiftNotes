package com.example.liftnotes.implementations

import com.example.liftnotes.interfaces.ViewExercisesRepository
import com.example.liftnotes.model.Exercise
import com.example.liftnotes.model.ResultOf
import com.example.liftnotes.test.testSessionsModel
import kotlinx.coroutines.delay

class ViewExercisesRepositoryImpl : ViewExercisesRepository {

    override suspend fun getCurrentExercises(sessionId: Int): ResultOf<List<Exercise>> {
        try {
            delay(1000)
            for (session in testSessionsModel) {
                if (sessionId == session.id) {
                    return ResultOf.Success(session.exercises)
                }
            }
            return ResultOf.Error("Couldn't find Session")
        } catch (e: Exception) {
            return ResultOf.Error(e.message)
        }
    }
}