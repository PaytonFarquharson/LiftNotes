package com.example.liftnotes.repository.model

import com.example.liftnotes.database.model.Exercise
import com.example.liftnotes.database.model.Session

data class ViewExercisesScreenData(val session: Session, val exercises: List<Exercise>)