package com.example.liftnotes.di

import com.example.liftnotes.implementations.SettingsRepositoryImpl
import com.example.liftnotes.implementations.ViewExercisesRepositoryImpl
import com.example.liftnotes.implementations.ViewSessionsRepositoryImpl
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
        return ViewSessionsRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideViewExercisesRepository(): ViewExercisesRepository {
        return ViewExercisesRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(): SettingsRepository {
        return SettingsRepositoryImpl()
    }
}