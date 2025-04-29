package com.example.liftnotes.implementations

import com.example.liftnotes.interfaces.ViewSessionsRepository
import com.example.liftnotes.test.testCurrentSessionsModel

class ViewSessionsRepositoryImpl : ViewSessionsRepository {

    override suspend fun getCurrentSessions() = testCurrentSessionsModel
}