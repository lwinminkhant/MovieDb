package com.lmkhant.moviedb.presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.lmkhant.moviedb.R
import com.lmkhant.moviedb.utils.Utils
import com.lmkhant.moviedb.utils.shimmerEffect

@Composable
fun MoviePosterImage(
    modifier: Modifier = Modifier,
    posterPath: String?
) {
    val posterImage = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data("${Utils.PREFIX_URL}$posterPath")
            .memoryCachePolicy(CachePolicy.ENABLED)
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

            is AsyncImagePainter.State.Empty,
            is AsyncImagePainter.State.Error -> {
                MissingPoster()
            }

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

@OptIn(ExperimentalCoilApi::class)
@Composable
fun MovieBackdropImage(
    modifier: Modifier = Modifier,
    posterPath: String?
) {
    val posterImage = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data("${Utils.PREFIX_URL}$posterPath")
            .crossfade(true)
            .memoryCachePolicy(CachePolicy.ENABLED)
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

            is AsyncImagePainter.State.Empty,
            is AsyncImagePainter.State.Error -> {
                MissingPoster()
            }

            is AsyncImagePainter.State.Loading  -> {
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

@Composable
fun MovieCastImage(
    modifier: Modifier = Modifier,
    posterPath: String?
) {
    val posterImage = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data("${Utils.PREFIX_URL}$posterPath")
            .crossfade(true)
            .build()
    )
    Box {
        when (posterImage.state) {
            is AsyncImagePainter.State.Success -> {
                Image(
                    painter = posterImage,
                    contentDescription = stringResource(R.string.movie_poster_content_description),
                    contentScale = ContentScale.Crop,
                    modifier = modifier.clip(RoundedCornerShape(5.dp))
                )
            }

            is AsyncImagePainter.State.Empty,
            is AsyncImagePainter.State.Error -> {
                MissingPoster()
            }

            is AsyncImagePainter.State.Loading -> {
                Image(
                    painter = posterImage,
                    contentDescription = stringResource(R.string.movie_poster_content_description),
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .alpha(0.5f)
                        .shimmerEffect()
                )
            }
        }
    }
}