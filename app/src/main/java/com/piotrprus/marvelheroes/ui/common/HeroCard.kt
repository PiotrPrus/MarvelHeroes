package com.piotrprus.marvelheroes.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import com.piotrprus.marvelheroes.data.model.CharacterItem

@Composable
fun HeroCard(modifier: Modifier = Modifier, hero: CharacterItem, onClick: (Int) -> Unit) {
    Card(modifier = modifier, elevation = 4.dp) {
        Box(
            modifier = Modifier.clickable { onClick(hero.id) },
            contentAlignment = Alignment.BottomCenter
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(hero.imageUrl)
                    .crossfade(true)
                    .build(), contentDescription = "${hero.name} poster",
                loading = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .placeholder(
                                true,
                                highlight = PlaceholderHighlight.shimmer(),
                                color = MaterialTheme.colors.onSurface
                            )
                    )
                },
                contentScale = ContentScale.Fit
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colors.secondary)
                    .padding(8.dp),
                text = hero.name,
                style = MaterialTheme.typography.button,
                color = MaterialTheme.colors.onSecondary
            )
        }
    }
}