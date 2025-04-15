package com.example.liftnotes.ui.screens.view_exercises

import com.example.liftnotes.model.Exercise
import com.example.liftnotes.test.testSessionsModel

class ViewExercisesRepository(val sessionId: Int) {
    fun getCurrentExercises() : List<Exercise> {
        for (session in testSessionsModel) {
            if (sessionId == session.id) {
                return session.exercises
            }
        }
        return emptyList()
    }
}