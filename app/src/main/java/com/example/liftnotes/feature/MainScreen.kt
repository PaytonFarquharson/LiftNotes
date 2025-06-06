package com.example.liftnotes.feature

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.liftnotes.R
import com.example.liftnotes.feature.BottomNavItem.History
import com.example.liftnotes.feature.BottomNavItem.Library
import com.example.liftnotes.feature.BottomNavItem.Settings
import com.example.liftnotes.feature.BottomNavItem.Workout
import com.example.liftnotes.navigation.RootNavGraph
import com.example.liftnotes.navigation.RootNavRoute

@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    navController.addOnDestinationChangedListener { controller, destination, _ ->
        if (controller.graph.findStartDestination().id == destination.id) {
            selectedIndex = 0
        }
    }

    Column (
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        RootNavGraph(
            navController = navController,
            modifier = Modifier.weight(1f)
        )

        NavigationBar (
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            val bottomNavItemList = listOf(
                Workout, Library, History, Settings
            )
            bottomNavItemList.forEachIndexed { index, navItem ->
                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                        selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        indicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                    ),
                    selected = selectedIndex == index,
                    onClick = {
                        selectedIndex = index
                        navController.navigate(navItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(navItem.icon),
                            contentDescription = navItem.label,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {
                        Text(
                            text = navItem.label,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                )
            }
        }
    }
}

sealed class BottomNavItem(val route: String, @DrawableRes val icon: Int, val label: String) {
    object Workout : BottomNavItem(RootNavRoute.Workout.route, R.drawable.ic_dumbbell, "Workout")
    object Library : BottomNavItem(RootNavRoute.Workout.route, R.drawable.ic_book, "Library")
    object History : BottomNavItem(RootNavRoute.Workout.route, R.drawable.ic_history, "History")
    object Settings : BottomNavItem(RootNavRoute.Settings.route, R.drawable.ic_settings, "Settings")
}