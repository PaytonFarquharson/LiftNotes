package com.example.liftnotes.di

import android.content.Context
import androidx.room.Room
import com.example.liftnotes.database.LiftNotesDao
import com.example.liftnotes.database.LiftNotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        callback: LiftNotesDatabase.Callback
    ): LiftNotesDatabase {
        return Room.databaseBuilder(
            context,
            LiftNotesDatabase::class.java,
            "liftnotes.db"
        )
            .addCallback(callback)
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(database: LiftNotesDatabase): LiftNotesDao = database.liftNotesDao()

    @Provides
    @Singleton
    fun provideDatabaseCallback(
        daoProvider: Provider<LiftNotesDao>,
        @Qualifiers.ApplicationScope scope: CoroutineScope
    ): LiftNotesDatabase.Callback {
        return LiftNotesDatabase.Callback(daoProvider, scope)
    }

    @Provides
    @Qualifiers.ApplicationScope
    @Singleton
    fun provideApplicationScope(): CoroutineScope = CoroutineScope(SupervisorJob())
}