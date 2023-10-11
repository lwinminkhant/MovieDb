package com.lmkhant.moviedb.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lmkhant.moviedb.R
import com.lmkhant.moviedb.domain.model.movie.Movie
import com.lmkhant.moviedb.mock.movie1
import com.valentinilk.shimmer.shimmer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieCardWithFav(
    movie: Movie,
    modifier: Modifier = Modifier,
    navigateToDetails: (Int) -> Unit,
    addToFavourite: (Movie) -> Unit,
) {
    Column(modifier = modifier
        .width(150.dp)) {
        Card(
            shape = RoundedCornerShape(15.dp),
            elevation = CardDefaults.cardElevation(5.dp),
            onClick = { navigateToDetails(movie.id) },
            modifier = modifier
                .height(200.dp)
                .width(150.dp)
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
            modifier = modifier.padding(top = 8.dp, bottom = 8.dp),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleSmall,
            maxLines = 1
        )

        Row {
            Icon(
                painter = painterResource(id = if (movie.favourite) R.drawable.ic_favorite_24 else R.drawable.ic_favorite_border_24),
                contentDescription = "favourite",
                modifier = modifier.clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    addToFavourite(movie)
                }
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "${(movie.voteAverage.toFloat() * 10).toInt()} %")
        }
    }
}


@Composable
fun LoadingMovieCardWithFav(modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = modifier
            .height(200.dp)
            .width(150.dp)
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .shimmer()
                .background(MaterialTheme.colorScheme.onSecondaryContainer)
        )
    }
}

@Composable
fun ErrorMovieCardWithFav(modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = modifier
            .height(200.dp)
            .width(150.dp)
    ) {
        Box(modifier = modifier.fillMaxSize()) {
            Box(
                modifier.background(MaterialTheme.colorScheme.onErrorContainer)
            )
            ErrorMessage(
                iconSize = 50.dp,
                textSize = 18.sp,
                errorColor = MaterialTheme.colorScheme.error,
                alpha = 1f
            )
        }
    }
}

@Preview
@Composable
fun ErrorMovieCardWithFavPreview() {
    ErrorMovieCardWithFav()
}
@Preview
@Composable
fun MovieCardWithFavPreview() {
    MovieCardWithFav(movie = movie1, addToFavourite = {}, navigateToDetails = {})
}