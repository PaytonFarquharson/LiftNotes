package com.example.liftnotes.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.example.liftnotes.ui.screens.view_exercises.ViewExercisesScreen
import com.example.liftnotes.ui.screens.view_sessions.ViewSessionsScreen

fun NavGraphBuilder.workoutNavGraph(navController: NavHostController) {
    navigation<Graph.WorkoutGraph>(
        startDestination = Route.ViewSessions
    ) {
        composable<Route.ViewSessions> {
            ViewSessionsScreen(
                onSessionClick = { navController.navigate(Route.ViewExercises(it)) }
            )
        }
        composable<Route.ViewExercises> { backStackEntry ->
            val viewExercises = backStackEntry.toRoute<Route.ViewExercises>()
            ViewExercisesScreen(
                viewExercises.sessionId,
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}