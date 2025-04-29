package com.example.liftnotes.interfaces

import com.example.liftnotes.model.CurrentSession

interface ViewSessionsRepository {

    suspend fun getCurrentSessions(): List<CurrentSession>
}