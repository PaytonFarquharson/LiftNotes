package com.example.liftnotes.di

import android.content.Context
import androidx.room.Room
import com.example.liftnotes.database.LiftNotesDao
import com.example.liftnotes.database.LiftNotesDatabase
import com.example.liftnotes.repository.implementations.HistoryRepositoryImpl
import com.example.liftnotes.repository.implementations.SettingsRepositoryImpl
import com.example.liftnotes.repository.implementations.WorkoutRepositoryImpl
import com.example.liftnotes.repository.interfaces.HistoryRepository
import com.example.liftnotes.repository.interfaces.SettingsRepository
import com.example.liftnotes.repository.interfaces.WorkoutRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): LiftNotesDatabase {
        return Room.databaseBuilder(context, LiftNotesDatabase::class.java, "liftnotes.db").build()
    }

    @Provides
    @Singleton
    fun provideDao(database: LiftNotesDatabase): LiftNotesDao = database.liftNotesDao()

    @Provides
    @Singleton
    fun provideWorkoutRepository(dao: LiftNotesDao): WorkoutRepository {
        return WorkoutRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideHistoryRepository(dao: LiftNotesDao): HistoryRepository {
        return HistoryRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(dao: LiftNotesDao): SettingsRepository {
        return SettingsRepositoryImpl(dao)
    }
}