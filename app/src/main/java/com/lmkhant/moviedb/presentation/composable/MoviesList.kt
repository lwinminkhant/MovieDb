package com.lmkhant.moviedb.presentation.composable

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.lmkhant.moviedb.R
import com.lmkhant.moviedb.domain.model.movie.Movie
import com.lmkhant.moviedb.presentation.MovieGridUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MoviesLists(
    modifier: Modifier = Modifier,
    popularMoviesState: MovieGridUiState,
    upComingMoviesState: MovieGridUiState,
    navigateToDetails: (Int) -> Unit,
    addToFavourite: (Movie) -> Unit,
    fetchNewMovies: () -> Unit,
) {
    var refreshing by remember { mutableStateOf(false) }

    LaunchedEffect(refreshing) {
        if (refreshing) {
            fetchNewMovies()
            delay(2000)
            refreshing = false
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = refreshing),
        onRefresh = { refreshing = true },
    ) {
        val scrollState = rememberScrollState()
        val lazyGridState = rememberLazyGridState()
        LazyVerticalGrid(

            columns = GridCells.Fixed(1),
            state = lazyGridState
        ) {

            item {
                HeaderText(stringResource(R.string.ms_popular_title))
                Spacer(modifier = Modifier.height(10.dp))
            }
            item {
                RenderMoviesList(
                    moviesUiState = popularMoviesState,
                    navigateToDetails = navigateToDetails,
                    addToFavourite = addToFavourite
                )
            }
            item {
                HeaderText(stringResource(R.string.ms_upcoming_title))
                Spacer(modifier = Modifier.height(10.dp))
            }
            item {
                RenderUpComingMoviesList(
                    moviesUiState = upComingMoviesState,
                    navigateToDetails = navigateToDetails,
                    addToFavourite = addToFavourite,
                    scrollState
                )
            }

        }

    }
}

@Composable
fun HeaderText(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.Bold
        ),
        modifier = Modifier.fillMaxWidth()
    )
}


@Composable
fun RenderMoviesList(
    moviesUiState: MovieGridUiState,
    navigateToDetails: (Int) -> Unit,
    addToFavourite: (Movie) -> Unit
) {
    when (moviesUiState) {
        is MovieGridUiState.Loading -> LoadingListOfMovies()
        is MovieGridUiState.Success -> ListOfMovies(
            moviesUiState.data,
            navigateToDetails = navigateToDetails,
            addToFavourite = addToFavourite
        )

        is MovieGridUiState.Error -> ErrorListOfMovies()
    }
}

@Composable
fun RenderUpComingMoviesList(
    moviesUiState: MovieGridUiState,
    navigateToDetails: (Int) -> Unit,
    addToFavourite: (Movie) -> Unit,
    scrollState: ScrollState
) {
    when (moviesUiState) {
        is MovieGridUiState.Loading -> LoadingListOfUpComingMovies()
        is MovieGridUiState.Success -> ListOfUpComingMovies(
            moviesUiState.data,
            navigateToDetails = navigateToDetails,
            addToFavourite = addToFavourite,
            scrollState = scrollState
        )

        is MovieGridUiState.Error -> ErrorListOfUpComingMovies()
    }
}

@Composable
fun LoadingListOfMovies() {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
        items(15) {
            LoadingMovieCardWithFav()
        }
    }
}

@Composable
fun LoadingListOfUpComingMovies() {
    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        (1..15).forEach { _ ->
            LoadingUpComingMovieCard()
        }
    }
}

@Composable
fun ListOfMovies(
    movies: StateFlow<List<Movie>>,
    navigateToDetails: (Int) -> Unit,
    addToFavourite: (Movie) -> Unit
) {
    val moviesState = movies.collectAsStateWithLifecycle(initialValue = emptyList())
    LazyHorizontalGrid(
        modifier = Modifier.height(300.dp),
        rows = GridCells.Fixed(1),
    ) {
        items(moviesState.value.size) { index ->
            Box(modifier = Modifier.padding(4.dp), contentAlignment = Alignment.Center) {
                MovieCardWithFav(
                    movie = moviesState.value[index],
                    navigateToDetails = navigateToDetails,
                    addToFavourite = addToFavourite
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ListOfUpComingMovies(
    movies: Flow<List<Movie>>,
    navigateToDetails: (Int) -> Unit,
    addToFavourite: (Movie) -> Unit,
    scrollState: ScrollState
) {
    val moviesState = movies.collectAsStateWithLifecycle(initialValue = emptyList())

    FlowColumn(content = {
        moviesState.value.forEach {
            Box(modifier = Modifier.padding(4.dp), contentAlignment = Alignment.Center) {
                Spacer(modifier = Modifier.height(20.dp))
                UpComingMovieCard(
                    movie = it,
                    navigateToDetails = navigateToDetails,
                    addToFavourite = addToFavourite
                )
            }
        }
    })
}

@Composable
fun ErrorListOfUpComingMovies() {
    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        (1..15).forEach { _ ->
            ErrorUpComingMovieCard()
        }
    }
}

@Composable
fun ErrorListOfMovies() {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
        items(15) {
            ErrorMovieCardWithFav()
        }
    }
}