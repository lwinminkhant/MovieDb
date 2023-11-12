package com.lmkhant.moviedb.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.lmkhant.moviedb.R
import com.lmkhant.moviedb.domain.model.genre.Genre
import com.lmkhant.moviedb.presentation.screen.favorite.FavouriteScreen
import com.lmkhant.moviedb.presentation.screen.more.AboutScreen
import com.lmkhant.moviedb.presentation.screen.more.MoreScreen
import com.lmkhant.moviedb.presentation.screen.more.settings.SettingScreen
import com.lmkhant.moviedb.presentation.screen.more.settings.SettingScreenViewModel
import com.lmkhant.moviedb.presentation.screen.movie.MoviesScreen
import com.lmkhant.moviedb.presentation.screen.movie.MoviesViewModel
import com.lmkhant.moviedb.presentation.screen.moviedetails.MovieDetailsViewModel
import com.lmkhant.moviedb.presentation.screen.moviedetails.RenderMovie
import com.lmkhant.moviedb.ui.navigation.BottomBar
import com.lmkhant.moviedb.ui.navigation.Destinations
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first

@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MoviesApp(modifier: Modifier = Modifier) {
    val navController = rememberAnimatedNavController()
    val navScreens = listOf(Destinations.Home, Destinations.Favorite, Destinations.More)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val slideEnterHorizontally = remember {
        slideInHorizontally(animationSpec = tween(durationMillis = 50)) { fullWidth ->
            -fullWidth / 3
        } + fadeIn(
            animationSpec = tween(durationMillis = 100)
        )
    }
    val slideOutHorizontally = remember {
        slideOutHorizontally(animationSpec = tween(durationMillis = 50)) { fullWidth ->
            -fullWidth / 3
        } + fadeOut(
            animationSpec = tween(durationMillis = 100)
        )
    }
    val movieDetailsViewModel = hiltViewModel<MovieDetailsViewModel>()
    val viewModel: SettingScreenViewModel = hiltViewModel()

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        bottomBar = { BottomBar(navController, navScreens) },
        topBar = {
            when (navBackStackEntry?.destination?.route) {
                Destinations.Home.route -> {
                    TopAppBar(title = { Text(text = stringResource(R.string.welcome)) })
                }

                Destinations.Settings.route -> {
                    TopAppBar(
                        title = { Text(text = stringResource(R.string.settings)) },
                        navigationIcon = {
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = stringResource(R.string.back)
                                )
                            }
                        })
                }


                Destinations.Favorite.route -> {
                    TopAppBar(
                        title = { Text(text = stringResource(R.string.favorite)) },
                    )

                }

                Destinations.About.route -> {
                    TopAppBar(title = { Text(text = stringResource(R.string.moviedb)) },
                        navigationIcon = {
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = stringResource(R.string.back)
                                )
                            }
                        })
                }

                Destinations.MoviesDetails.route -> {
                    /*TopAppBar(title = { Text(text = stringResource(R.string.movie_detail)) },
                        navigationIcon = {
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = stringResource(id = R.string.back)
                                )
                            }
                        })*/
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            AnimatedNavHost(
                navController = navController,
                startDestination = Destinations.Home.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                modifier = modifier
            ) {
                composable(
                    Destinations.Home.route,
                ) {
                    val moviesViewModel = hiltViewModel<MoviesViewModel>()
                    val popularMovies =
                        moviesViewModel.popularMoviesUiState.collectAsState().value
                    val upComingMovies =
                        moviesViewModel.upComingMoviesUiState.collectAsState().value

                    val searchResult =
                        moviesViewModel.searchedMoviesUiState.collectAsStateWithLifecycle(
                            initialValue = MovieGridUiState.Loading
                        ).value
                    MoviesScreen(
                        navigateToDetails = { movieId: Int ->
                            navController.navigate("MovieDetailsScreen/$movieId") {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        popularMoviesState = popularMovies,
                        upComingMoviesState = upComingMovies,
                        addToFavourite = { movie ->
                            moviesViewModel.updateMovie(movie.copy(favourite = !movie.favourite))
                        },
                        fetchNewMovies = { moviesViewModel.fetchNewMovies() },
                        searchedMovies = { term ->
                            moviesViewModel.searchMovies(term)
                        },
                        searchedMoviesUiStateList = searchResult
                    )
                }
                composable(
                    Destinations.MoviesDetails.route,
                    enterTransition = {
                        EnterTransition.None
                    },
                    exitTransition = {
                        ExitTransition.None
                    },
                    popEnterTransition = {
                        EnterTransition.None
                    },
                    popExitTransition = {
                        ExitTransition.None
                    },
                ) { arg ->
                    val movieIdArg = remember { arg.arguments?.getString("movieId")?.toInt() }
                    val movieDetails =
                        movieDetailsViewModel.movieDetailsUiState.collectAsStateWithLifecycle(
                            initialValue = MovieUiState.Loading
                        ).value

                    val genres = movieDetailsViewModel.genres.collectAsStateWithLifecycle(
                        initialValue = GenreUiState.Loading
                    ).value



                    val movieCreditsUiState = movieDetailsViewModel.movieCreditsUiState
                    movieIdArg?.let {
                        LaunchedEffect(movieIdArg) {
                            movieDetailsViewModel.setMovieDetail(movieIdArg)

                        }
                        RenderMovie(
                            navController = navController,
                            movieUiState = movieDetails,
                            genres = genres,
                            movieCreditsUiState = movieCreditsUiState.collectAsStateWithLifecycle(
                                initialValue = MovieCreditsUiState.Loading
                            ).value,
                            addToFavourite = { movie ->
                                movieDetailsViewModel.updateMovie(movie.copy(favourite = !movie.favourite))
                            }
                        )
                    }
                }

                composable(route = Destinations.Favorite.route) {
                    val moviesViewModel = hiltViewModel<MoviesViewModel>()
                    val state =
                        moviesViewModel.favoriteMoviesUiState.collectAsStateWithLifecycle(
                            initialValue = MovieGridUiState.Loading
                        ).value
                    FavouriteScreen(Modifier, state) { movieId: Int ->
                        navController.navigate("MovieDetailsScreen/$movieId") {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
                composable(
                    route = Destinations.More.route,
                    enterTransition = {
                        slideEnterHorizontally
                    },
                    exitTransition = {
                        slideOutHorizontally
                    },
                ) {
                    MoreScreen(navController)
                }
                composable(
                    route = Destinations.Settings.route,
                    enterTransition = {
                        slideEnterHorizontally
                    },
                    exitTransition = {
                        slideOutHorizontally
                    },
                ) {
                    MoreScreen(rememberAnimatedNavController())
                    val state by viewModel.settingScreenState.collectAsStateWithLifecycle()
                    SettingScreen(
                        state = state,
                        onEvent = viewModel::onEvent,
                        navController = navController
                    )
                }
                composable(route = Destinations.About.route) {
                    AboutScreen()
                }
            }
        }
    }
}