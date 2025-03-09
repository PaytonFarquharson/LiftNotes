package com.example.liftnotes.test

import com.example.liftnotes.R
import com.example.liftnotes.model.Exercise
import com.example.liftnotes.model.Reps
import com.example.liftnotes.model.Session
import com.example.liftnotes.model.Set

val testExercisesModel: List<Exercise> = listOf(
    Exercise("0", "Pull-Ups", "Underhand", R.drawable.ic_pull_up, listOf(Set(0f, "", Reps(5,8)))),
    Exercise("1", "Upright Rows", "Barbell", R.drawable.ic_barbell_fixed, listOf(Set(20f, "", Reps(6,12)))),
    Exercise("2", "Flat Bench Press", "Smith Machine", R.drawable.ic_bench_press, listOf(Set(160f, "", Reps(5,8)))),
    Exercise("3", "Landmine Rows", "Neutral Grip", R.drawable.ic_landmine, listOf(Set(0f, "", Reps(6,12)))),
    Exercise("4", "Dips", null, R.drawable.ic_dip_handles, listOf(Set(0f, "", Reps(5,8)))),
    Exercise("5", "Preacher Curls", "EZ Bar", R.drawable.ic_ez_bar, listOf(Set(20f, "", Reps(6,12)))),

    Exercise("6", "Barbell Rows", "Underhand", R.drawable.ic_barbell, listOf(Set(120f, "", Reps(5,8)))),
    Exercise("7", "Incline Bench Press", "Dumbbells", R.drawable.ic_dumbbell_pair, listOf(Set(90f, "", Reps(5,8)))),
    Exercise("8", "Pullovers", "EZ Bar", R.drawable.ic_ez_bar_fixed, listOf(Set(80f, "", Reps(5,8)))),
    Exercise("9", "Chest Flys", "Dumbbells", R.drawable.ic_dumbbell_pair, listOf(Set(20f, "", Reps(6,12)))),
    Exercise("10", "Lateral Raises", "Cables", R.drawable.ic_cable_handle, listOf(Set(25f, "", Reps(6,12)))),
    Exercise("11", "Cable Curls", null, R.drawable.ic_cable_handle, listOf(Set(40f, "", Reps(6,12)))),
    Exercise("12", "Cable Rows", "Overhand", R.drawable.ic_lat_pulldown_low_row, listOf(Set(140f, "", Reps(6,12)))),

    Exercise("13", "Hamstring Curls", "Seated", R.drawable.ic_leg_machine, listOf(Set(110f, "", Reps(6,12)))),
    Exercise("14", "Barbell Squats", null, R.drawable.ic_squat_rack, listOf(Set(180f, "", Reps(5,8)))),
    Exercise("15", "Calf Raises", "Leg Press Machine", R.drawable.ic_leg_press, listOf(Set(240f, "", Reps(6,12)))),
    Exercise("16", "Leg Extensions", null, R.drawable.ic_leg_machine, listOf(Set(160f, "", Reps(6,12)))),
    Exercise("17", "Good Mornings", "Smith Machine", R.drawable.ic_squat_rack, listOf(Set(110f, "", Reps(5,8)))),
)

val testSessionsModel: List<Session> = listOf(
    Session("1", "Upper Day 1", "Cherry St YMCA", R.drawable.ic_pull_up, testExercisesModel.subList(0,6)),
    Session("2", "Upper Day 2", "Central YMCA", R.drawable.ic_bench_pressing, testExercisesModel.subList(6,13)),
    Session("3", "Lower Day 1", "Cherry St YMCA", R.drawable.ic_stretch5, testExercisesModel.subList(13,16)),
    Session("4", "Lower Day 2", "Cherry St YMCA", R.drawable.ic_stretch5, listOf(testExercisesModel[13],testExercisesModel[17],testExercisesModel[16],testExercisesModel[15])),
)