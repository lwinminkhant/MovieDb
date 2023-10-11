package com.lmkhant.moviedb.presentation.screen.movie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lmkhant.moviedb.domain.model.movie.Movie
import com.lmkhant.moviedb.presentation.MovieGridUiState
import com.lmkhant.moviedb.presentation.composable.MoviesLists
import com.lmkhant.moviedb.presentation.composable.SearchBar
import com.lmkhant.moviedb.presentation.screen.favorite.FavouriteScreen
import kotlinx.coroutines.delay

@Composable
fun MoviesScreen(
    modifier: Modifier = Modifier,
    popularMoviesState: MovieGridUiState,
    upComingMoviesState: MovieGridUiState,
    navigateToDetails: (Int) -> Unit,
    searchedMoviesUiStateList: MovieGridUiState,
    searchedMovies: (String) -> Unit,
    addToFavourite: (Movie) -> Unit,
    fetchNewMovies: () -> Unit,
) {
    val searchTerm = remember { mutableStateOf("") }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
    ) {
        LaunchedEffect(searchTerm.value) {
            delay(1000)
            searchedMovies(searchTerm.value)
        }
        SearchBar(searchTerm)
        Spacer(modifier.height(20.dp))
        if (searchTerm.value.isEmpty()) {
            MoviesLists(
                popularMoviesState = popularMoviesState,
                upComingMoviesState = upComingMoviesState,
                navigateToDetails = navigateToDetails,
                addToFavourite = addToFavourite,
                fetchNewMovies = fetchNewMovies
            )
        } else {
            FavouriteScreen(favoriteMoviesStateList = searchedMoviesUiStateList, navigateToDetails = navigateToDetails)
        }

    }
}