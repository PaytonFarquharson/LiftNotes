package com.example.liftnotes.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.liftnotes.feature.view_exercises.ViewExercisesScreen
import com.example.liftnotes.feature.view_exercises.ViewExercisesUiEffect
import com.example.liftnotes.feature.view_exercises.ViewExercisesViewModel
import com.example.liftnotes.feature.view_sessions.ViewSessionsScreen
import com.example.liftnotes.feature.view_sessions.ViewSessionsUiEffect
import com.example.liftnotes.feature.view_sessions.ViewSessionsViewModel
import kotlinx.coroutines.flow.collectLatest

fun NavGraphBuilder.workoutNavGraph(navController: NavHostController) {
    navigation(
        route = RootNavRoute.Workout.route,
        startDestination = WorkoutRoute.ViewSessions.route
    ) {
        composable(WorkoutRoute.ViewSessions.route) {
            val viewModel: ViewSessionsViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val bottomSheetState by viewModel.bottomSheetState.collectAsStateWithLifecycle()
            LaunchedEffect(Unit) {
                viewModel.effect.collectLatest {
                    when (it) {
                        is ViewSessionsUiEffect.NavigateToSession -> {
                            navController.navigate(WorkoutRoute.ViewExercises.createRoute(it.sessionId))
                        }
                    }
                }
            }

            ViewSessionsScreen(
                uiState = uiState,
                onEvent = { viewModel.onUiEvent(it) },
                bottomSheetState = bottomSheetState,
                onBottomSheetEvent = { viewModel.onBottomSheetEvent(it) }
            )
        }
        composable(
            WorkoutRoute.ViewExercises.route,
            arguments = listOf(navArgument(WorkoutRoute.ARG_SESSION_ID) { type = NavType.IntType })
        ) {
            val viewModel: ViewExercisesViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val bottomSheetState by viewModel.bottomSheetState.collectAsStateWithLifecycle()
            LaunchedEffect(Unit) {
                viewModel.effect.collectLatest {
                    when (it) {
                        is ViewExercisesUiEffect.NavigateBack -> {
                            navController.popBackStack()
                        }
                    }
                }
            }

            ViewExercisesScreen(
                uiState = uiState,
                onEvent = { viewModel.onUiEvent(it) },
                bottomSheetState = bottomSheetState,
                onBottomSheetEvent = { viewModel.onBottomSheetEvent(it) }
            )
        }
    }
}

sealed class WorkoutRoute(val route: String) {
    object ViewSessions : WorkoutRoute("view_sessions")
    object ViewExercises : WorkoutRoute("view_exercises/{$ARG_SESSION_ID}") {
        fun createRoute(exerciseId: Int) = "view_exercises/$exerciseId"
    }

    companion object {
        const val ARG_SESSION_ID = "sessionId"
    }
}