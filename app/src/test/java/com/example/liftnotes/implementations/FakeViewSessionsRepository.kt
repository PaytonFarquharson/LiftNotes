package com.example.liftnotes.implementations

import com.example.liftnotes.interfaces.ViewSessionsRepository
import com.example.liftnotes.model.CurrentSession
import com.example.liftnotes.model.ResultOf
import com.example.liftnotes.test.testCurrentSessionsModel

class FakeViewSessionsRepository: ViewSessionsRepository {
    override suspend fun getCurrentSessions(): ResultOf<List<CurrentSession>> {
        return ResultOf.Success(testCurrentSessionsModel)
    }
}