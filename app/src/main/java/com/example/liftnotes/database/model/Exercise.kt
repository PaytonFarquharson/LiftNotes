package com.example.liftnotes.database.model

import android.icu.text.DecimalFormat
import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.liftnotes.R

@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String? = null,
    @DrawableRes val imageId: Int = R.drawable.ic_empty,
    val weight: Float? = null,
    val sets: Int? = null,
    val reps: Range? = null,
    val time: Int? = null,
    val rating: Rating = Rating.NONE
)

fun getWeightString(weight: Float?): String {
    val builder = StringBuilder()
    weight?.let {
        val formatter = android.icu.text.DecimalFormat("0.#")
        builder.append("${formatter.format(weight)} lbs")
    }
    return builder.toString()
}

fun getSetsString(sets: Int?, reps: Range?): String {
    val builder = StringBuilder()
    sets?.let {
        builder.append(it)
        if (reps != null) builder.append(" x ")
    }
    reps?.let {
        builder.append(it.min)
        if (it.max != null) {
            builder.append("-${it.max}")
        }
    }
    return builder.toString()
}

fun getTimeString(time: Int?): String {
    val builder = StringBuilder()
    time?.let {
        val hours: Int = time / 3600
        val minutes: Int = (time % 3600) / 60
        val seconds: Int = time % 60

        if (hours > 0) {
            builder.append("${hours}h")
            if (minutes > 0 || seconds > 0) builder.append(" ")
        }
        if (minutes > 0) {
            builder.append("${minutes}m")
            if (seconds > 0) builder.append(" ")
        }
        if (seconds > 0) {
            builder.append("${seconds}s")
        }
    }
    return builder.toString()
}

data class Range(
    val min: Int,
    val max: Int? = null
)

enum class Rating {
    NONE,
    VERY_EASY,
    EASY,
    MEDIUM,
    HARD,
    VERY_HARD
}