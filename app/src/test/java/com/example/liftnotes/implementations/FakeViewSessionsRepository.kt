package com.example.liftnotes.implementations

import com.example.liftnotes.repository.model.CurrentSession
import com.example.liftnotes.repository.model.DataResult

class FakeViewSessionsRepository(
    private val result: DataResult<List<CurrentSession>>
): ViewSessionsRepository {
    override suspend fun getCurrentSessions(): DataResult<List<CurrentSession>> {
        return result
    }
}