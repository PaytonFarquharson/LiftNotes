package com.example.liftnotes.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.liftnotes.database.model.CurrentSessionIds
import com.example.liftnotes.database.model.Exercise
import com.example.liftnotes.database.model.HistoricalExercise
import com.example.liftnotes.database.model.HistoricalSession
import com.example.liftnotes.database.model.Session
import com.example.liftnotes.database.model.Settings
import kotlinx.coroutines.flow.Flow

@Dao
interface LiftNotesDao {
    @Insert
    suspend fun insertHistoricalSession(session: HistoricalSession)
    @Insert
    suspend fun insertHistoricalExercise(exercise: HistoricalExercise)

    @Upsert
    suspend fun upsertSetting(setting: Settings)
    @Upsert
    suspend fun upsertCurrentSessions(current: CurrentSessionIds)
    @Upsert
    suspend fun upsertSession(session: Session): Long
    @Upsert
    suspend fun upsertExercise(exercise: Exercise): Long

    @Delete
    suspend fun deleteSession(session: Session)
    @Delete
    suspend fun deleteExercise(exercise: Exercise)
    @Delete
    suspend fun deleteHistoricalSession(session: HistoricalSession)
    @Delete
    suspend fun deleteHistoricalExercise(exercise: HistoricalExercise)

    @Query("SELECT * FROM settings WHERE id = 0") fun getSettings(): Flow<Settings?>
    @Query("SELECT * FROM currentSessions WHERE id = 0") fun getCurrentSessionIds(): Flow<CurrentSessionIds?>
    @Query("SELECT * FROM sessions WHERE id = :id") fun getSession(id: Int): Flow<Session?>
    @Query("SELECT * FROM sessions") fun getSessions(): Flow<List<Session>>
    @Query("SELECT * FROM exercises") fun getExercises(): Flow<List<Exercise>>
    @Query("SELECT * FROM historicalSessions WHERE id = :id") fun getHistoricalSession(id: Int): Flow<HistoricalSession?>
    @Query("SELECT * FROM historicalSessions") fun getHistoricalSessions(): Flow<List<HistoricalSession>>
    @Query("SELECT * FROM historicalExercises") fun getHistoricalExercises(): Flow<List<HistoricalExercise>>
}