package com.example.liftnotes.database

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.liftnotes.database.model.CurrentSessionIds
import com.example.liftnotes.database.model.Exercise
import com.example.liftnotes.database.model.HistoricalExercise
import com.example.liftnotes.database.model.HistoricalSession
import com.example.liftnotes.database.model.Range
import com.example.liftnotes.database.model.Rating
import com.example.liftnotes.database.model.Session
import com.example.liftnotes.database.model.Settings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDateTime
import javax.inject.Provider

class Converters {
    @TypeConverter
    fun fromDayOfWeekList(days: List<DayOfWeek>): String = days.joinToString(",") { it.name }

    @TypeConverter
    fun toDayOfWeekList(data: String): List<DayOfWeek> =
        if (data.isBlank()) emptyList() else data.split(",").map { DayOfWeek.valueOf(it) }

    @TypeConverter
    fun fromRange(range: Range?): String? = range?.let { "${it.min},${it.max}" }

    @TypeConverter
    fun toRange(data: String?): Range? = data?.split(",")?.map { it.toInt() }?.let { Range(it[0], it[1]) }

    @TypeConverter
    fun fromRating(rating: Rating): Int = rating.ordinal

    @TypeConverter
    fun toRating(value: Int): Rating = Rating.entries[value]

    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime): String = dateTime.toString()

    @TypeConverter
    fun toLocalDateTime(data: String): LocalDateTime = LocalDateTime.parse(data)

    @TypeConverter
    fun fromIntList(list: List<Int>): String = list.joinToString(",")

    @TypeConverter
    fun toIntList(data: String): List<Int> =
        if (data.isBlank()) emptyList() else data.split(",").map { it.toInt() }
}

@Database(
    entities = [
        Settings::class,
        Session::class,
        Exercise::class,
        CurrentSessionIds::class,
        HistoricalSession::class,
        HistoricalExercise::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class LiftNotesDatabase : RoomDatabase() {
    abstract fun liftNotesDao(): LiftNotesDao

    class Callback(
        private val daoProvider: Provider<LiftNotesDao>,
        private val scope: CoroutineScope
    ): RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            scope.launch {
                val dao = daoProvider.get()
                dao.upsertSetting(Settings())
                dao.upsertCurrentSessions(CurrentSessionIds())
            }
        }
    }
}
