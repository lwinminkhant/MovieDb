package com.lmkhant.moviedb.presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.lmkhant.moviedb.R

@Composable
fun MissingPoster(
    modifier: Modifier = Modifier
) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Image(
            painter = painterResource(id = R.drawable.missing_poster),
            contentDescription = stringResource(id = R.string.loading_error_content_description),
            contentScale = ContentScale.Crop,
            modifier = modifier
                .alpha(0.5f)
        )
    }

}