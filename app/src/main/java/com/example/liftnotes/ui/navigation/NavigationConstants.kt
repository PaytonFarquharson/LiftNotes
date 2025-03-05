package com.example.liftnotes.ui.navigation

private object Route {
    const val HOME = "home"
    const val SESSION = "session"
    const val EXERCISE = "exercise"
    const val SETTINGS = "settings"
}

sealed class Screen (val route: String) {
    object Home: Screen(Route.HOME)
    object Session: Screen(Route.SESSION)
    object Exercise: Screen(Route.EXERCISE)
    object Settings: Screen(Route.SETTINGS)
}