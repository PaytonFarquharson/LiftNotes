package com.example.liftnotes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.liftnotes.feature.settings.SettingsScreen
import kotlinx.serialization.Serializable

@Composable
fun RootNavGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = Graph.WorkoutGraph,
        modifier = modifier
    ) {
        workoutNavGraph(navController = navController)
        composable(Screen.Settings.route) {
            SettingsScreen()
        }
    }
}

@Serializable
sealed interface Graph {
    @Serializable
    data object WorkoutGraph: Graph

    @Serializable
    data object ArchiveGraph: Graph
}


sealed class Screen(val route: String) {
    object ViewSessions: Screen("view_sessions")
    object ViewExercises: Screen("view_exercises")
    object Settings: Screen("settings")
}