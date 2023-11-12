package com.lmkhant.moviedb.presentation.composable

import android.content.res.Configuration.UI_MODE_NIGHT_UNDEFINED
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.lmkhant.moviedb.domain.model.movie.Movie
import com.lmkhant.moviedb.mock.movie1
import com.lmkhant.moviedb.mock.movies

@Composable
fun MovieGrid(
    movies: List<Movie>,
    navigateToDetails: (Int) -> Unit,
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 4.dp), contentAlignment = Alignment.TopStart
    ) {
        if (movies.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No favourites yet!")
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                content = {
                    items(movies.size) { index ->
                        Box(
                            modifier = Modifier.padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            MovieCard(
                                movie = movies[index],
                                navigateToDetails = navigateToDetails,
                                addToFavourite = {}
                            )
                        }
                    }
                })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieCard(
    movie: Movie,
    modifier: Modifier = Modifier,
    navigateToDetails: (Int) -> Unit,
    addToFavourite: (Movie) -> Unit,
) {
    Column(modifier = modifier.width(170.dp)) {
        Card(
            shape = RoundedCornerShape(15.dp),
            elevation = CardDefaults.cardElevation(5.dp),
            onClick = { navigateToDetails(movie.id) },
            modifier = modifier
                .height(200.dp)
                .width(170.dp)
        ) {
            Box(
                modifier = modifier.fillMaxSize()
            ) {
                MoviePosterImage(
                    posterPath = movie.posterPath,
                )
            }
        }
        Text(
            text = movie.title,
            modifier = modifier
                .padding(top = 8.dp, bottom = 8.dp)
                .width(170.dp),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleSmall,
            maxLines = 1
        )
    }
}

@Preview
@Composable
fun MovieCardPreview() {
    MovieCard(movie = movie1, navigateToDetails = {}, addToFavourite = {})
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    backgroundColor = 1929292,
    uiMode = UI_MODE_NIGHT_UNDEFINED,
    wallpaper = Wallpapers.RED_DOMINATED_EXAMPLE
)
@Composable
fun MovieCardGrid() {
    MovieGrid(movies = movies, navigateToDetails = {})
}