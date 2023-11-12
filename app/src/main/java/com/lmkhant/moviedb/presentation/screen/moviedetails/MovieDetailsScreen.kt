package com.lmkhant.moviedb.presentation.screen.moviedetails

import android.content.res.Configuration
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.lmkhant.moviedb.R
import com.lmkhant.moviedb.domain.model.genre.Genre
import com.lmkhant.moviedb.domain.model.movie.Movie
import com.lmkhant.moviedb.mock.movie1
import com.lmkhant.moviedb.presentation.GenreUiState
import com.lmkhant.moviedb.presentation.MovieCreditsUiState
import com.lmkhant.moviedb.presentation.MovieUiState
import com.lmkhant.moviedb.presentation.composable.ListOfCast
import com.lmkhant.moviedb.presentation.composable.MovieBackdropImage
import com.lmkhant.moviedb.ui.theme.spacing
import com.lmkhant.moviedb.utils.formatDate
import kotlinx.coroutines.flow.first
import org.w3c.dom.Text
import java.nio.file.Files.find
import java.util.Locale

@Composable
fun MovieDetailsScreen(
    navController: NavController,
    movie: Movie,
    genreUiState: GenreUiState,
    movieCreditsUiState: MovieCreditsUiState,
    addToFavourite: (movie: Movie) -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)

        ) {
            Card {
                MovieBackdropImage(
                    posterPath = movie.backdropPath, modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }
            Icon(
                modifier = Modifier
                    .padding(20.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        navController.popBackStack()
                    },
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onBackground
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
                    text = "UA | ${formatDate(movie.releaseDate)}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Text(text = "${movie.voteCount} votes")

            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                var genres: List<Genre> = emptyList()
                when (genreUiState) {
                    is GenreUiState.Success -> {
                        genres =
                            genreUiState.data.collectAsStateWithLifecycle(initialValue = emptyList()).value
                    }

                    else -> {
                    }
                }
                Text(
                    text = "1hr 43min |${
                        movie.genreIds.joinToString(separator = ", ") { id ->
                            genres.find { it.id == id }?.name ?: "Unknown"
                        }
                    }",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Text(text = Locale("en").displayLanguage, Modifier.padding(end = 10.dp))
                Box(modifier = Modifier.background(Color.Gray)) {
                    Text(text = "2D", modifier = Modifier.padding(4.dp))
                }
            }

            Text(
                text = stringResource(R.string.movie_description),
                style = MaterialTheme.typography.titleLarge
            )
            Text(text = movie.overview, style = MaterialTheme.typography.bodyMedium)


            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Casts", Modifier.padding(top = 16.dp, bottom = 16.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(text = "View all")
            }
            Row(Modifier.fillMaxWidth()) {
                when (movieCreditsUiState) {
                    is MovieCreditsUiState.Loading -> {
                        LinearProgressIndicator(Modifier.fillMaxWidth())
                    }

                    is MovieCreditsUiState.Success -> {
                        ListOfCast(casts = movieCreditsUiState.data)
                    }

                    else -> {}
                }
            }

            Button(onClick = {}, modifier = Modifier.padding(top = 16.dp), shape = RoundedCornerShape(5.dp)) {
                Text(
                    text = "Book Tickets",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            /*Row(
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
            Text(text = movie.overview, style = MaterialTheme.typography.bodyMedium)*/
        }
    }
}

@Composable
fun RenderMovie(
    navController: NavController,
    movieUiState: MovieUiState,
    genres: GenreUiState,
    movieCreditsUiState: MovieCreditsUiState,
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
                    navController,
                    it, addToFavourite = addToFavourite,
                    genreUiState = genres,
                    movieCreditsUiState = movieCreditsUiState
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

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MovieDetailsScreenPreview() {
    MovieDetailsScreen(
        rememberNavController(),
        movie1,
        GenreUiState.Loading,
        MovieCreditsUiState.Loading,
        {})
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun MovieDetailsScreenPreviewLight() {
    MovieDetailsScreen(
        rememberNavController(),
        movie1,
        GenreUiState.Loading,
        MovieCreditsUiState.Loading,
        {})
}