package com.example.liftnotes.implementations

import com.example.liftnotes.interfaces.ViewExercisesRepository
import com.example.liftnotes.model.Exercise
import com.example.liftnotes.test.testSessionsModel

class ViewExercisesRepositoryImpl : ViewExercisesRepository {

    override suspend fun getCurrentExercises(sessionId: Int): List<Exercise> {
        for (session in testSessionsModel) {
            if (sessionId == session.id) {
                return session.exercises
            }
        }
        return emptyList()
    }
}