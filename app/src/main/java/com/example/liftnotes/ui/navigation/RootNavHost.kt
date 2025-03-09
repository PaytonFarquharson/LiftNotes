package com.example.liftnotes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.liftnotes.ui.screens.edit_exercise.EditExerciseScreen
import com.example.liftnotes.ui.screens.edit_session.EditSessionScreen
import com.example.liftnotes.ui.screens.settings.SettingsScreen
import com.example.liftnotes.ui.screens.view_exercises.ViewExercisesScreen
import com.example.liftnotes.ui.screens.view_sessions.ViewSessionsScreen

@Composable
fun RootNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.ViewSessions.route
    ) {
        composable(Screen.ViewSessions.route) {
            ViewSessionsScreen(navController)
        }
        composable(Screen.EditSession.route) {
            EditSessionScreen()
        }
        composable(Screen.ViewExercises.route) {
            ViewExercisesScreen(navController)
        }
        composable(Screen.EditExercise.route) {
            EditExerciseScreen()
        }
        composable(Screen.Settings.route) {
            SettingsScreen()
        }
    }
}