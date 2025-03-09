package com.example.liftnotes.ui.screens.view_exercises

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun ViewExercisesScreen(
    navController: NavController,
    viewModel: ViewExercisesViewModel = viewModel(factory = ViewExercisesViewModel.provideFactory())
) {
}