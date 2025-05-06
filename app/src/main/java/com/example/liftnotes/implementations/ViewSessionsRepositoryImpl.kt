package com.example.liftnotes.implementations

import com.example.liftnotes.interfaces.ViewSessionsRepository
import com.example.liftnotes.model.CurrentSession
import com.example.liftnotes.model.ResultOf
import com.example.liftnotes.test.testCurrentSessionsModel
import kotlinx.coroutines.delay

class ViewSessionsRepositoryImpl : ViewSessionsRepository {

    override suspend fun fetchCurrentSessions(): ResultOf<List<CurrentSession>> {
        try {
            delay(1000)
            return ResultOf.Success(testCurrentSessionsModel)
        } catch(e: Exception) {
            return ResultOf.Error(e.message)
        }
    }
}