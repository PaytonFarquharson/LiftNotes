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
        composable<Route.Settings> {
            SettingsScreen()
        }
    }
}

object Graph {
    @Serializable
    object WorkoutGraph

    @Serializable
    object ArchiveGraph
}

object Route {
    @Serializable
    object ViewSessions

    @Serializable
    data class ViewExercises(val sessionId: Int)

    @Serializable
    object Settings
}