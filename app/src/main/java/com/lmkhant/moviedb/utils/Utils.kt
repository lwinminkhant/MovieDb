package com.lmkhant.moviedb.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.core.net.toUri

class Utils {
    companion object{
        const val BASE_URL = "https://api.themoviedb.org"
        const val PREFIX_URL = "https://image.tmdb.org/t/p/original"
    }
}
fun getBitMap(context: Context, pictureUri: String): Bitmap =
    if (Build.VERSION.SDK_INT < 28) {
        MediaStore.Images
            .Media.getBitmap(context.contentResolver, pictureUri.toUri())

    } else {
        val source = ImageDecoder
            .createSource(context.contentResolver, pictureUri.toUri())
        ImageDecoder.decodeBitmap(source)
    }

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition(label = "")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        ), label = ""
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.surfaceVariant,
                MaterialTheme.colorScheme.surface,
                MaterialTheme.colorScheme.surfaceVariant,
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat()),
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}