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
    fun insertHistoricalSession(session: HistoricalSession)
    @Insert
    fun insertHistoricalExercise(exercise: HistoricalExercise)

    @Upsert
    fun upsertSetting(setting: Settings)
    @Upsert
    fun upsertCurrentSessions(current: CurrentSessionIds)
    @Upsert
    fun upsertSession(session: Session)
    @Upsert
    fun upsertExercise(exercise: Exercise)

    @Delete
    fun deleteSession(session: Session)
    @Delete
    fun deleteExercise(exercise: Exercise)
    @Delete
    fun deleteHistoricalSession(session: HistoricalSession)
    @Delete
    fun deleteHistoricalExercise(exercise: HistoricalExercise)

    @Query("SELECT * FROM settings WHERE id = 0") fun getSettings(): Flow<Settings?>
    @Query("SELECT * FROM currentSessions WHERE id = 0") fun getCurrentSessionIds(): Flow<CurrentSessionIds?>
    @Query("SELECT * FROM sessions WHERE id = :id") fun getSession(id: Int): Flow<Session?>
    @Query("SELECT * FROM sessions") fun getSessions(): Flow<List<Session>>
    @Query("SELECT * FROM exercises") fun getExercises(): Flow<List<Exercise>>
    @Query("SELECT * FROM historicalSessions WHERE id = :id") fun getHistoricalSession(id: Int): Flow<HistoricalSession?>
    @Query("SELECT * FROM historicalSessions") fun getHistoricalSessions(): Flow<List<HistoricalSession>>
    @Query("SELECT * FROM historicalExercises") fun getHistoricalExercises(): Flow<List<HistoricalExercise>>
}