package com.example.liftnotes.ui.navigation

private object Route {
    const val VIEW_SESSIONS = "viewSessions"
    const val EDIT_SESSION = "editSession"
    const val VIEW_EXERCISES = "viewExercise"
    const val EDIT_EXERCISE = "editExercise"
    const val SETTINGS = "settings"
}

sealed class Screen (val route: String) {
    object ViewSessions: Screen(Route.VIEW_SESSIONS)
    object EditSession: Screen(Route.EDIT_SESSION)
    object ViewExercises: Screen(Route.VIEW_EXERCISES)
    object EditExercise: Screen(Route.EDIT_EXERCISE)
    object Settings: Screen(Route.SETTINGS)
}