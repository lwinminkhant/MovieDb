package com.lmkhant.moviedb.presentation.screen.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lmkhant.moviedb.mock.movies
import com.lmkhant.moviedb.presentation.MovieGridUiState
import com.lmkhant.moviedb.presentation.composable.ErrorListOfMovies
import com.lmkhant.moviedb.presentation.composable.MovieGrid
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun FavouriteScreen(
    modifier: Modifier = Modifier,
    favoriteMoviesStateList: MovieGridUiState,
    navigateToDetails: (Int) -> Unit,
) {
    MoviesGridState(
        moviesUiState = favoriteMoviesStateList,
        navigateToDetails = navigateToDetails
    )
}

@Composable
fun MoviesGridState(
    moviesUiState: MovieGridUiState,
    navigateToDetails: (Int) -> Unit
) {
    when (moviesUiState) {
        is MovieGridUiState.Loading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        is MovieGridUiState.Success -> MovieGrid(
            moviesUiState.data.collectAsStateWithLifecycle(initialValue = emptyList()).value,
            navigateToDetails = navigateToDetails
        )

        is MovieGridUiState.Error -> ErrorListOfMovies()
    }
}

@Preview
@Composable
fun MovieGridUiStatePreview() {
    val uiState = MovieGridUiState.Success(data = MutableStateFlow(movies))
    MoviesGridState(moviesUiState = uiState, navigateToDetails = {})
}