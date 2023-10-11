package com.lmkhant.moviedb.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destinations(
    val route: String,
    val title: String?,
    val icon: ImageVector?,
) {
    object Home: Destinations(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )
    object Favorite: Destinations(
        route = "favorite",
        title = "Favourite",
        icon = Icons.Default.Favorite
    )
    object More: Destinations(
        route = "more",
        title = "More",
        icon = Icons.Default.MoreHoriz
    )

    object MoviesDetails : Destinations(
        "MovieDetailsScreen/{movieId}",
        null,
        null
    )
    object Settings : Destinations(
        "settings",
        "Settings",
        null
    )
    object About : Destinations(
        "about",
        "About",
        null
    )

}