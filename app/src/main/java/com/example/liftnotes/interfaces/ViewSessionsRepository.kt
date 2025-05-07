package com.example.liftnotes.interfaces

import com.example.liftnotes.model.CurrentSession
import com.example.liftnotes.model.ResultOf

interface ViewSessionsRepository {

    suspend fun getCurrentSessions(): ResultOf<List<CurrentSession>>
}