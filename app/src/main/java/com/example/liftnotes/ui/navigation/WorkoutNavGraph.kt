package com.example.liftnotes.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.example.liftnotes.ui.screens.edit_exercise.EditExerciseScreen
import com.example.liftnotes.ui.screens.edit_session.EditSessionScreen
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
        composable<Route.EditSession> {
            EditSessionScreen()
        }
        composable<Route.ViewExercises> { backStackEntry ->
            val viewExercises = backStackEntry.toRoute<Route.ViewExercises>()
            ViewExercisesScreen(
                viewExercises.sessionId,
                onExerciseClick = { navController.navigate(Route.EditExercise) },
                navigateBack = { navController.popBackStack() }
            )
        }
        composable<Route.EditExercise> {
            EditExerciseScreen()
        }
    }
}