package com.lmkhant.moviedb.presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.lmkhant.moviedb.R
import com.lmkhant.moviedb.utils.shimmerEffect

@OptIn(ExperimentalCoilApi::class)
@Composable
fun MoviePosterImage(
    modifier: Modifier = Modifier,
    posterPath: String?
) {
    val posterImage = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(posterPath)
            .crossfade(true)
            .build()
    )
    Box(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
        when (posterImage.state) {
            is AsyncImagePainter.State.Success -> {
                Image(
                    painter = posterImage,
                    contentDescription = stringResource(R.string.movie_poster_content_description),
                    contentScale = ContentScale.Crop,
                    modifier = modifier.fillMaxSize()
                )
            }

            is AsyncImagePainter.State.Error -> {
                MissingPoster()
            }

            is AsyncImagePainter.State.Empty -> {}

            is AsyncImagePainter.State.Loading -> {
                Image(
                    painter = posterImage,
                    contentDescription = stringResource(R.string.movie_poster_content_description),
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .fillMaxSize()
                        .alpha(0.5f)
                        .shimmerEffect()
                )
            }
        }
    }
}