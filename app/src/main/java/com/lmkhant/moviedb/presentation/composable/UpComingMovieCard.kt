package com.lmkhant.moviedb.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lmkhant.moviedb.R
import com.lmkhant.moviedb.domain.model.movie.Movie
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import com.valentinilk.shimmer.unclippedBoundsInWindow

@Composable
fun UpComingMovieCard(
    movie: Movie,
    modifier: Modifier = Modifier,
    navigateToDetails: (Int) -> Unit,
    addToFavourite: (Movie) -> Unit,
) {
    Row(
        modifier = modifier
            .clickable {
                navigateToDetails(movie.id)
            }
            .fillMaxWidth()
    ) {
        Card(
            shape = RoundedCornerShape(15.dp),
            elevation = CardDefaults.cardElevation(5.dp),
            modifier = modifier
                .width(150.dp)
                .height(180.dp)
                .padding(end = 16.dp)
        ) {
            Box(
                modifier = modifier.fillMaxSize()
            ) {
                MoviePosterImage(
                    posterPath = movie.posterPath,
                )
            }
        }
        Column {
            Text(
                text = movie.title,
                modifier = modifier.padding(bottom = 8.dp),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1
            )
            Text(
                text = movie.overview,
                modifier.height(100.dp),
                style = MaterialTheme.typography.bodySmall,
                overflow = TextOverflow.Ellipsis
            )

            Row(Modifier.padding(top = 16.dp)) {
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


                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_message_24),
                    tint = MaterialTheme.colorScheme.tertiary,
                    contentDescription = "comment",
                    modifier = modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {

                    }
                )
                Spacer(modifier = Modifier.width(6.dp))

                Text(text = if (movie.voteCount > 1000) "${movie.voteCount / 1000} K" else movie.voteCount.toString())

            }
        }
    }
}
@Composable
fun LoadingUpComingMovieCard(modifier: Modifier = Modifier) {
    val shimmerInstance = rememberShimmer(ShimmerBounds.Custom)

    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Card(
            shape = RoundedCornerShape(15.dp),
            elevation = CardDefaults.cardElevation(5.dp),
            modifier = modifier
                .width(150.dp)
                .height(180.dp)
                .padding(end = 16.dp)
        ) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .shimmer()
                    .background(MaterialTheme.colorScheme.onSecondaryContainer)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned { layoutCoordinates ->
                    val position = layoutCoordinates.unclippedBoundsInWindow()
                    shimmerInstance.updateBounds(position)
                },
        ) {
            Card(
                modifier = modifier
                    .padding(bottom = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(150.dp)
                        .height(15.dp)
                        .shimmer()
                        .background(MaterialTheme.colorScheme.onSecondaryContainer),
                )
            }

            Card(
                modifier = modifier
                    .padding(bottom = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(170.dp)
                        .height(15.dp)
                        .shimmer()
                        .background(MaterialTheme.colorScheme.onSecondaryContainer),
                )
            }

            Card(
                modifier = modifier
                    .padding(bottom = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(180.dp)
                        .height(15.dp)
                        .shimmer()
                        .background(MaterialTheme.colorScheme.onSecondaryContainer),
                )
            }

            Card(
                modifier = modifier
                    .padding(bottom = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(130.dp)
                        .height(15.dp)
                        .shimmer()
                        .background(MaterialTheme.colorScheme.onSecondaryContainer),
                )
            }
        }
    }
}

@Composable
fun ErrorUpComingMovieCard(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Card(
            shape = RoundedCornerShape(15.dp),
            elevation = CardDefaults.cardElevation(5.dp),
            modifier = modifier
                .width(150.dp)
                .height(180.dp)
                .padding(end = 16.dp)
        ) {
            ErrorMessage(
                iconSize = 50.dp,
                textSize = 18.sp,
                errorColor = MaterialTheme.colorScheme.error,
                alpha = 1f
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned { layoutCoordinates ->
                    val position = layoutCoordinates.unclippedBoundsInWindow()
                },
        ) {
            Card(
                modifier = modifier
                    .padding(bottom = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(150.dp)
                        .height(15.dp)
                        .background(MaterialTheme.colorScheme.onSecondary),
                )
            }

            Card(
                modifier = modifier
                    .padding(bottom = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(170.dp)
                        .height(15.dp)
                        .background(MaterialTheme.colorScheme.onSecondary),
                )
            }

            Card(
                modifier = modifier
                    .padding(bottom = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(180.dp)
                        .height(15.dp)
                        .background(MaterialTheme.colorScheme.onSecondary),
                )
            }

            Card(
                modifier = modifier
                    .padding(bottom = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(130.dp)
                        .height(15.dp)
                        .background(MaterialTheme.colorScheme.onSecondary),
                )
            }
        }
    }
}

@Preview
@Composable
fun LoadingMovieCardPreview() {
    LoadingUpComingMovieCard()

}

@Preview
@Composable
fun ErrorUpComingMovieCardPreview() {
    ErrorUpComingMovieCard()
}

@Preview
@Composable
fun UpComingMovieCardPreview() {
    val movie3 = Movie(
        adult = false,
        backdropPath = "/backdrop3.jpg",
        id = 3,
        originalLanguage = "en",
        originalTitle = "Movie 3 This is the overview of Movie Three.",
        overview = "This is the overview of Movie Three.",
        popularity = 67.89,
        posterPath = "/poster3.jpg",
        releaseDate = "2023-09-10",
        title = "Movie Three",
        video = true,
        voteAverage = 8.2,
        voteCount = 1200,
        favourite = false,
        type = "upComing",
        index = 1
    )
    UpComingMovieCard(movie = movie3, addToFavourite = {}, navigateToDetails = {})


}