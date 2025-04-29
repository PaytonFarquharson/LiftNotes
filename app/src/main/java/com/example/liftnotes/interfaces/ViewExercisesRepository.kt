package com.example.liftnotes.interfaces

import com.example.liftnotes.model.Exercise

interface ViewExercisesRepository {

    suspend fun getCurrentExercises(sessionId: Int) : List<Exercise>
}