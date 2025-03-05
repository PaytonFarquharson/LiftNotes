package com.example.liftnotes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.liftnotes.ui.screens.exercise.ExerciseScreen
import com.example.liftnotes.ui.screens.home.HomeScreen
import com.example.liftnotes.ui.screens.session.SessionScreen
import com.example.liftnotes.ui.screens.settings.SettingsScreen

@Composable
fun RootNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen()
        }
        composable(Screen.Session.route) {
            SessionScreen()
        }
        composable(Screen.Exercise.route) {
            ExerciseScreen()
        }
        composable(Screen.Settings.route) {
            SettingsScreen()
        }
    }
}