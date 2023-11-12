package com.lmkhant.moviedb.presentation.composable

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lmkhant.moviedb.domain.model.credits.Cast
import com.lmkhant.moviedb.domain.model.credits.Credit
import com.lmkhant.moviedb.domain.model.credits.Crew
import com.lmkhant.moviedb.utils.Utils
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

@Composable
fun ListOfCast(casts: List<Cast>) {
    Row(
        Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        casts.onEach {
            Timber.d("Profile Path: ${it.profilePath}")
            MovieCastImage(Modifier.width(100.dp).height(120.dp), "${Utils.PREFIX_URL}${it.profilePath}")
        }
    }
}

@Composable
fun ListOfCrew(crews: List<Crew>) {
    Row(
        Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        crews.onEach {
            Timber.d("Profile Path: ${it.profilePath}")
            MovieCastImage(Modifier.width(100.dp).height(120.dp), "${Utils.PREFIX_URL}${it.profilePath}")
        }
    }
}
