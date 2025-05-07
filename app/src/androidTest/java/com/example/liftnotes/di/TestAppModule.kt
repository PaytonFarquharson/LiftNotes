package com.example.liftnotes.di

import com.example.liftnotes.implementations.FakeSettingsRepository
import com.example.liftnotes.implementations.FakeViewExercisesRepository
import com.example.liftnotes.implementations.FakeViewSessionsRepository
import com.example.liftnotes.interfaces.SettingsRepository
import com.example.liftnotes.interfaces.ViewExercisesRepository
import com.example.liftnotes.interfaces.ViewSessionsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideViewSessionsRepository(): ViewSessionsRepository {
        return com.example.liftnotes.implementations.FakeViewSessionsRepository()
    }

    @Provides
    @Singleton
    fun provideViewExercisesRepository(): ViewExercisesRepository {
        return com.example.liftnotes.implementations.FakeViewExercisesRepository()
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(): SettingsRepository {
        return com.example.liftnotes.implementations.FakeSettingsRepository()
    }
}