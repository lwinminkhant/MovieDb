package com.lmkhant.moviedb.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(
    navController: NavHostController,
    navItem: List<Destinations>,
) {
    val navBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()
    val currentRoute: String? = navBackStackEntry?.destination?.route
    val shouldBeVisible = rootDestinations.contains(currentRoute)
    AnimatedVisibility(
        visible = shouldBeVisible,
        enter = EnterTransition.None,
        exit = ExitTransition.None
    ) {
        NavigationBar(
            windowInsets = NavigationBarDefaults.windowInsets,
            tonalElevation = 0.dp,

            modifier = Modifier
                .fillMaxWidth(),

            ) {
            val route = navBackStackEntry?.destination
            navItem.map { navItem ->
                NavigationBarItem(
                    icon = {
                        navItem.icon?.let {
                            Icon(
                                imageVector = it,
                                contentDescription = navItem.title
                            )
                        }
                    },
                    label = {
                        navItem.title?.let {
                            Text(
                                it,
                                style = MaterialTheme.typography.labelSmall,
                            )
                        }
                    },
                    selected = currentRoute == navItem.route,
                    alwaysShowLabel = true,
                    onClick = {
                        navController.navigate(navItem.route) {
                            popUpTo(id = navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
            }
        }
    }
}


