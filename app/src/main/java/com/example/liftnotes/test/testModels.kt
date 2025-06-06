package com.example.liftnotes.test

import com.example.liftnotes.R
import com.example.liftnotes.repository.model.CompletionDay
import com.example.liftnotes.repository.model.CurrentSession
import com.example.liftnotes.database.model.Exercise
import com.example.liftnotes.database.model.Range
import com.example.liftnotes.database.model.Session
import com.example.liftnotes.database.model.Settings
import java.time.DayOfWeek

val testExercisesModel: List<Exercise> = listOf(
    Exercise(0, "Pull-Ups", "Underhand", R.drawable.ic_pull_up, null, null,4, Range(5,8)),
    Exercise(1, "Upright Rows", "Barbell", R.drawable.ic_barbell_fixed, 20f, 5f,4, Range(6,12)),
    Exercise(2, "Flat Bench Press", "Smith Machine", R.drawable.ic_bench_press, 160f, 25f,4, Range(5,8)),
    Exercise(3, "Landmine Rows", "Neutral Grip", R.drawable.ic_landmine, null, null,4, Range(6,12)),
    Exercise(4, "Dips", null, R.drawable.ic_dip_handles, null, null,4, Range(5,8)),
    Exercise(5, "Preacher Curls", "EZ Bar", R.drawable.ic_ez_bar, 20f, 5f, 4, Range(6,12)),

    Exercise(6, "Barbell Rows", "Underhand", R.drawable.ic_barbell, 120f, 10f,4, Range(5,8)),
    Exercise(7, "Incline Bench Press", "Dumbbells", R.drawable.ic_dumbbell_pair, 90f, 10f, 4, Range(5,8)),
    Exercise(8, "Pullovers", "EZ Bar", R.drawable.ic_ez_bar_fixed, 80f, 10f, 4, Range(5,8)),
    Exercise(9, "Chest Flys", "Dumbbells", R.drawable.ic_dumbbell_pair, 20f, 2.5f, 4, Range(6,12)),
    Exercise(10, "Lateral Raises", "Cables", R.drawable.ic_cable_handle, 25f, 2.5f, 4, Range(6,12)),
    Exercise(11, "Cable Curls", null, R.drawable.ic_cable_handle, 40f, 5f, 4, Range(6,12)),
    Exercise(12, "Cable Rows", "Overhand", R.drawable.ic_lat_pulldown_low_row, 140f, 10f, 4, Range(6,12)),

    Exercise(13, "Hamstring Curls", "Seated", R.drawable.ic_leg_machine, 110f, 10f,4, Range(6,12)),
    Exercise(14, "Barbell Squats", null, R.drawable.ic_squat_rack, 180f, 25f,4, Range(5,8)),
    Exercise(15, "Calf Raises", "Leg Press Machine", R.drawable.ic_leg_press, 240f, 45f,4, Range(6,12), 125),
    Exercise(16, "Leg Extensions", null, R.drawable.ic_leg_machine, 160f, 25f, 4, Range(6,12)),
    Exercise(17, "Good Mornings", "Smith Machine", R.drawable.ic_squat_rack, 110f, 25f, 4, Range(5,8)),
)

val testSessionsModel: List<Session> = listOf(
    Session(1, "Upper Day 1", "Cherry St YMCA", R.drawable.ic_pull_up, listOf(DayOfWeek.TUESDAY), listOf(1, 2, 3, 4, 5, 6)),
    Session(2, "Upper Day 2", "Central YMCA", R.drawable.ic_bench_pressing, listOf(DayOfWeek.SATURDAY, DayOfWeek.TUESDAY, DayOfWeek.MONDAY, DayOfWeek.FRIDAY, DayOfWeek.THURSDAY, DayOfWeek.SUNDAY, DayOfWeek.WEDNESDAY), (6..13).toList()),
    Session(3, "Lower Day 1", "Cherry St YMCA", R.drawable.ic_stretch5, listOf(DayOfWeek.FRIDAY), listOf(13, 14, 15, 16)),
    Session(4, "Lower Day 2", "Cherry St YMCA", R.drawable.ic_stretch5, listOf(DayOfWeek.TUESDAY, DayOfWeek.MONDAY, DayOfWeek.FRIDAY), listOf(13,17,16,15)),
)

val testCurrentSessionsModel: List<CurrentSession> = listOf(
    CurrentSession(testSessionsModel[0], listOf(CompletionDay(DayOfWeek.TUESDAY, true))),
    CurrentSession(testSessionsModel[1], listOf(CompletionDay(DayOfWeek.SUNDAY, true), CompletionDay(DayOfWeek.MONDAY), CompletionDay(DayOfWeek.TUESDAY, true), CompletionDay(DayOfWeek.WEDNESDAY), CompletionDay(DayOfWeek.THURSDAY), CompletionDay(DayOfWeek.FRIDAY), CompletionDay(DayOfWeek.SATURDAY))),
    CurrentSession(testSessionsModel[2], listOf(CompletionDay(DayOfWeek.FRIDAY))),
    CurrentSession(testSessionsModel[3], listOf(CompletionDay(DayOfWeek.MONDAY), CompletionDay(DayOfWeek.TUESDAY), CompletionDay(DayOfWeek.FRIDAY)))
)