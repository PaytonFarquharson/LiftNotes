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
        startDestination = Screen.ViewSessions.route
    ) {
        composable(Screen.ViewSessions.route) {
            ViewSessionsScreen(
                onSessionClick = { navController.navigate("${Screen.ViewExercises.route}/$it") }
            )
        }
        composable(
            "${Screen.ViewExercises.route}/{id}",
            arguments = listOf(navArgument("id") {type = NavType.IntType})
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: -1
            ViewExercisesScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}