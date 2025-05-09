package com.example.liftnotes.interfaces

import com.example.liftnotes.model.Exercise
import com.example.liftnotes.model.ResultOf

interface ViewExercisesRepository {

    suspend fun getCurrentExercises(sessionId: Int) : ResultOf<List<Exercise>>
}