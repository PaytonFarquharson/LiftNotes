package com.example.liftnotes.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.liftnotes.feature.settings.SettingsScreen
import com.example.liftnotes.feature.settings.SettingsViewModel

@Composable
fun RootNavGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = RootNavRoute.Workout.route,
        modifier = modifier
    ) {
        workoutNavGraph(navController = navController)
        composable(RootNavRoute.Settings.route) {
            val viewModel: SettingsViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            SettingsScreen(uiState)
        }
    }
}

sealed class RootNavRoute(val route: String) {
    object Workout: RootNavRoute("workout")
    object Library: RootNavRoute("library")
    object History: RootNavRoute("history")
    object Settings: RootNavRoute("settings")
}