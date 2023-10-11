package com.lmkhant.moviedb.presentation.screen.moviedetails

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lmkhant.moviedb.R
import com.lmkhant.moviedb.domain.model.movie.Movie
import com.lmkhant.moviedb.mock.movie1
import com.lmkhant.moviedb.presentation.MovieUiState
import com.lmkhant.moviedb.presentation.composable.MoviePosterImage
import com.lmkhant.moviedb.ui.theme.spacing

@Composable
fun MovieDetailsScreen(movie: Movie, addToFavourite: (movie: Movie) -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())) {

        Card {
            MoviePosterImage(
                posterPath = movie.posterPath, modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
            )
        }
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Card(border = CardDefaults.outlinedCardBorder()) {
                Row(Modifier
                    .fillMaxWidth()
                    .padding(spacing.medium)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        addToFavourite(movie)
                    }, horizontalArrangement = Arrangement.Center
                ) {

                    Icon(
                        painter = painterResource(id = if (movie.favourite) R.drawable.ic_favorite_24 else R.drawable.ic_favorite_border_24),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(end = spacing.small)
                    )
                    Text(text = if (movie.favourite) "Remove from favourite" else "Add to favourite")
                }

            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )

            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text(
                    text = "Voting Average",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Text(text = "${(movie.voteAverage.toFloat() * 10).toInt()} %")

            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.total_votes),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )

                Text(text = "${movie.voteCount} votes")

            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.release_date),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )

                Text(text = movie.releaseDate)

            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text(
                    text = "Original language",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )

                Text(text = movie.originalLanguage)

            }
            Text(text = stringResource(R.string.movie_description), style = MaterialTheme.typography.titleMedium)
            Text(text = movie.overview, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun RenderMovie(
    movieUiState: MovieUiState,
    addToFavourite: (movie: Movie) -> Unit
) {
    when (movieUiState) {
        is MovieUiState.Loading -> {
            LoadingMovie()
        }

        is MovieUiState.Success -> {
            val movie =
                movieUiState.data.collectAsStateWithLifecycle(initialValue = null).value
            movie?.let {
                MovieDetailsScreen(
                    it, addToFavourite = addToFavourite
                )
            }
        }

        is MovieUiState.Error -> {
            ErrorMovie()
        }
    }
}

@Composable
fun LoadingMovie() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorMovie() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = stringResource(R.string.error_loading_movie))
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MovieDetailsScreenPreview() {
    MovieDetailsScreen(movie1, {})
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun MovieDetailsScreenPreviewLight() {
    MovieDetailsScreen(movie1, {})
}