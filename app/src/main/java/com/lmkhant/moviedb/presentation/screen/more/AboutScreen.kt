package com.lmkhant.moviedb.presentation.screen.more

import TypewriterTextEffect
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lmkhant.moviedb.R
import com.lmkhant.moviedb.ui.theme.spacing

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // App Name and Version

        Text(
            text = stringResource(id = R.string.app_name),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
        )
        Text(
            text = "Version 1.0",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
        )



        Card {
            val description = stringResource(id = R.string.about_screen_description)
            TypewriterTextEffect(text = description) { displayedText ->
                Text(
                    modifier = Modifier.fillMaxWidth().padding(spacing.medium),
                    text = displayedText,
                    style = MaterialTheme.typography.bodyMedium
                )

            }
        }
    }
}