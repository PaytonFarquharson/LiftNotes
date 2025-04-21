package com.example.liftnotes.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.liftnotes.model.Exercise
import com.example.liftnotes.model.Range
import com.example.liftnotes.model.Rating
import com.example.liftnotes.model.Session
import java.time.DayOfWeek
import java.time.LocalDateTime

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "liftnotes.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_SETTINGS = "settings"
        private const val TABLE_SESSIONS ="sessions"
        private const val TABLE_EXERCISES = "exercises"
        private const val TABLE_CURRENT = "current"
        private const val TABLE_HISTORICAL_SESSIONS ="historicalSessions"
        private const val TABLE_HISTORICAL_EXERCISES = "historicalExercises"

        private const val COLUMN_ID = "id"
        private const val COLUMN_SETTING = "setting"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_IMAGE_ID = "imageId"
        private const val COLUMN_SESSION_ID = "sessionId"
        private const val COLUMN_SESSION_IDS = "sessionIds"
        private const val COLUMN_EXERCISE_ID = "exerciseId"
        private const val COLUMN_EXERCISE_IDS = "exerciseIds"
        private const val COLUMN_HISTORICAL_EXERCISE_IDS = "historicalExerciseIds"
        private const val COLUMN_DAYS_OF_WEEK = "daysOfWeek"
        private const val COLUMN_WEIGHT = "weight"
        private const val COLUMN_SETS = "sets"
        private const val COLUMN_REPS_MIN = "repsMin"
        private const val COLUMN_REPS_MAX = "repsMax"
        private const val COLUMN_TIME = "time"
        private const val COLUMN_RATING = "rating"
        private const val COLUMN_DATE_TIME = "dateTime"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_SETTINGS ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_SETTING TEXT)")
        db?.execSQL("CREATE TABLE $TABLE_SESSIONS ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NAME TEXT, $COLUMN_DESCRIPTION TEXT, $COLUMN_IMAGE_ID INTEGER, $COLUMN_DAYS_OF_WEEK TEXT, $COLUMN_EXERCISE_IDS TEXT)")
        db?.execSQL("CREATE TABLE $TABLE_EXERCISES($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NAME TEXT, $COLUMN_DESCRIPTION TEXT, $COLUMN_IMAGE_ID INTEGER, $COLUMN_WEIGHT FLOAT, $COLUMN_SETS INTEGER, $COLUMN_REPS_MIN INTEGER, $COLUMN_REPS_MAX INTEGER, $COLUMN_TIME INTEGER, $COLUMN_RATING INTEGER)")
        db?.execSQL("CREATE TABLE $TABLE_CURRENT ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_SESSION_IDS TEXT)")
        db?.execSQL("CREATE TABLE $TABLE_HISTORICAL_SESSIONS ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_DATE_TIME TEXT, $COLUMN_SESSION_ID INTEGER, $COLUMN_NAME TEXT, $COLUMN_DESCRIPTION TEXT, $COLUMN_IMAGE_ID INTEGER, $COLUMN_HISTORICAL_EXERCISE_IDS TEXT)")
        db?.execSQL("CREATE TABLE $TABLE_HISTORICAL_EXERCISES ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_EXERCISE_ID INTEGER, $COLUMN_NAME TEXT, $COLUMN_DESCRIPTION TEXT, $COLUMN_IMAGE_ID INTEGER, $COLUMN_WEIGHT FLOAT, $COLUMN_SETS INTEGER, $COLUMN_REPS_MIN INTEGER, $COLUMN_REPS_MAX INTEGER, $COLUMN_TIME INTEGER, $COLUMN_RATING INTEGER)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_SETTINGS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_SESSIONS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_EXERCISES")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CURRENT")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_HISTORICAL_SESSIONS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_HISTORICAL_EXERCISES")

        val settingsValues = ContentValues().apply {
            //TODO: default settings
        }
        db?.insert(TABLE_SETTINGS, null, settingsValues)
        val currentValues = ContentValues().apply {
            put(COLUMN_SESSION_IDS, "")
        }
        db?.insert(TABLE_CURRENT, null, currentValues)

        onCreate(db)
    }

    fun createSession(name: String, description: String?, imageId: Int, daysOfWeek: List<DayOfWeek>) : Session {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_DESCRIPTION, description)
            put(COLUMN_IMAGE_ID, imageId)
            put(COLUMN_DAYS_OF_WEEK, daysOfWeek.joinToString { it.name })
            put(COLUMN_EXERCISE_IDS, "")
        }
        val id = db.insert(TABLE_SESSIONS, null, values)
        db.close()
        return Session(id.toInt(), name, description, imageId, daysOfWeek)
    }

    fun updateSession(session: Session) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, session.name)
            put(COLUMN_DESCRIPTION, session.description)
            put(COLUMN_IMAGE_ID, session.imageId)
            put(COLUMN_DAYS_OF_WEEK, session.daysOfWeek.joinToString { it.name })
            put(COLUMN_EXERCISE_IDS, session.exercises.joinToString { it.id.toString() })
        }
        db.update(
            TABLE_SESSIONS,
            values,
            "$COLUMN_ID=?",
            arrayOf(session.id.toString())
        )
        db.close()
    }

    fun deleteSession(id: Int) {
        val db = writableDatabase
        db.delete(
            TABLE_SESSIONS,
            "$COLUMN_ID=?",
            arrayOf(id.toString())
        )
        db.close()
    }

    fun createExercise(name: String, description: String?, imageId: Int, weight: Float?, sets: Int?, reps: Range?, time: Int?) : Exercise {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_DESCRIPTION, description)
            put(COLUMN_IMAGE_ID, imageId)
            put(COLUMN_WEIGHT, weight)
            put(COLUMN_SETS, sets)
            put(COLUMN_REPS_MIN, reps?.min)
            put(COLUMN_REPS_MAX, reps?.max)
            put(COLUMN_TIME, time)
            put(COLUMN_RATING, Rating.NONE.ordinal)
        }
        val id = db.insert(TABLE_EXERCISES, null, values)
        db.close()
        return Exercise(id.toInt(), name, description, imageId, weight, sets, reps, time)
    }

    fun updateExercise(exercise: Exercise) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, exercise.name)
            put(COLUMN_DESCRIPTION, exercise.description)
            put(COLUMN_IMAGE_ID, exercise.imageId)
            put(COLUMN_WEIGHT, exercise.weight)
            put(COLUMN_SETS, exercise.sets)
            put(COLUMN_REPS_MIN, exercise.reps?.min)
            put(COLUMN_REPS_MAX, exercise.reps?.max)
            put(COLUMN_TIME, exercise.time)
            put(COLUMN_RATING, exercise.rating.ordinal)
        }
        db.update(
            TABLE_EXERCISES,
            values,
            "$COLUMN_ID=?",
            arrayOf(exercise.id.toString())
        )
        db.close()
    }

    fun deleteExercise(id: Int) {
        val db = writableDatabase
        db.delete(
            TABLE_EXERCISES,
            "$COLUMN_ID=?",
            arrayOf(id.toString())
        )
        db.close()
    }

    fun updateCurrent(sessionIds: List<Int>) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_SESSION_IDS, sessionIds.joinToString { it.toString() })
        }
        db.update(
            TABLE_CURRENT,
            values,
            "$COLUMN_ID=?",
            arrayOf("0")
        )
        db.close()
    }

    fun createHistoricalSession(session: Session, dateTime: LocalDateTime) {
        val db = writableDatabase
        val historicalExerciseIds = mutableListOf<Int>()
        for (exercise in session.exercises) {
            val values = ContentValues().apply {
                put(COLUMN_EXERCISE_ID, exercise.id)
                put(COLUMN_NAME, exercise.name)
                put(COLUMN_DESCRIPTION, exercise.description)
                put(COLUMN_IMAGE_ID, exercise.imageId)
                put(COLUMN_WEIGHT, exercise.weight)
                put(COLUMN_SETS, exercise.sets)
                put(COLUMN_REPS_MIN, exercise.reps?.min)
                put(COLUMN_REPS_MAX, exercise.reps?.max)
                put(COLUMN_TIME, exercise.time)
                put(COLUMN_RATING, Rating.NONE.ordinal)
            }
            val id = db.insert(TABLE_HISTORICAL_EXERCISES, null, values)
            historicalExerciseIds.add(id.toInt())
        }

        val values = ContentValues().apply {
            put(COLUMN_DATE_TIME, dateTime.toString())
            put(COLUMN_SESSION_ID, session.id)
            put(COLUMN_NAME, session.name)
            put(COLUMN_DESCRIPTION, session.description)
            put(COLUMN_IMAGE_ID, session.imageId)
            put(COLUMN_HISTORICAL_EXERCISE_IDS, historicalExerciseIds.joinToString { it.toString() })
        }
        db.insert(TABLE_SESSIONS, null, values)
        db.close()
    }

    fun deleteHistoricalSession(historicalSession: Session) {
        val db = writableDatabase
        for (exercise in historicalSession.exercises) {
            db.delete(
                TABLE_HISTORICAL_EXERCISES,
                "$COLUMN_ID=?",
                arrayOf(exercise.id.toString())
            )
        }

        db.delete(
            TABLE_HISTORICAL_SESSIONS,
            "$COLUMN_ID=?",
            arrayOf(historicalSession.id.toString())
        )
        db.close()
    }
}