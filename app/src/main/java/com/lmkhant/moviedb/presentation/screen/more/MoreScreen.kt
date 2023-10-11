package com.lmkhant.moviedb.presentation.screen.more

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.lmkhant.moviedb.presentation.screen.more.component.MoreScreenButton
import com.lmkhant.moviedb.ui.navigation.Destinations
import com.lmkhant.moviedb.ui.theme.spacing

@Composable
fun MoreScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxWidth().padding(spacing.medium)
    ) {
        val context = LocalContext.current
        MoreScreenButton(
            text = "Settings",
            icon = Icons.Filled.Settings
        ) {
            navController.navigate(route = Destinations.Settings.route) {
                restoreState = true
            }
        }
        MoreScreenButton(
            text = "About",
            icon = Icons.Filled.Info,
            onClick = {
                navController.navigate(route = Destinations.About.route) {
                    restoreState = true
                }
            }
        )
        MoreScreenButton(
            text = "Privacy Policy",
            icon = Icons.Filled.Shield,
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://policies.google.com/privacy")
                context.startActivity(intent)
            }
        )
    }
}
