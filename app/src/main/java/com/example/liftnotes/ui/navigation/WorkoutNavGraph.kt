package com.example.liftnotes.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.liftnotes.feature.view_exercises.ViewExercisesScreen
import com.example.liftnotes.feature.view_sessions.ViewSessionsScreen

fun NavGraphBuilder.workoutNavGraph(navController: NavHostController) {
    navigation<Graph.WorkoutGraph>(
        startDestination = Route.ViewSessions
    ) {
        composable<Route.ViewSessions> {
            ViewSessionsScreen(
                onSessionClick = { navController.navigate("viewExercises/$it") }
            )
        }
        composable(
            "viewExercises/{id}",
            arguments = listOf(navArgument("id") {type = NavType.IntType})
        ) {
            ViewExercisesScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}