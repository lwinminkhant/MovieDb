package com.lmkhant.moviedb.presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lmkhant.moviedb.R

@Composable
fun SearchBar(
    searchTerm: MutableState<String>,
    modifier: Modifier = Modifier
) {
    val searchDefaultLabel = stringResource(R.string.search_bar_label)
    val label = remember { mutableStateOf(searchDefaultLabel) }
    Row(
        modifier
            .fillMaxWidth()
            .height(54.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Box(
            modifier = modifier
                .weight(1F)
                .background(
                    MaterialTheme.colorScheme.surfaceVariant,
                    RoundedCornerShape(15.dp)
                ),
        ) {
            Row {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = modifier.padding(15.dp)
                )
                Box {
                    Text(
                        text = label.value,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = modifier.padding(top = 15.dp, end = 15.dp, bottom = 15.dp),
                        fontSize = 16.sp
                    )
                    BasicTextField(
                        value = searchTerm.value,
                        onValueChange = {
                            searchTerm.value = it
                            if (searchTerm.value !== "") label.value = "" else label.value =
                                searchDefaultLabel
                        },
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
                        singleLine = true,
                        modifier = modifier
                            .padding(top = 15.dp, end = 15.dp, bottom = 15.dp),
                        textStyle = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            }
        }
        Card(
            shape = RoundedCornerShape(5.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.filter_horizental),
                contentDescription = "",
                modifier = Modifier.background(MaterialTheme.colorScheme.primary).size(45.dp)

            )
        }
    }
}