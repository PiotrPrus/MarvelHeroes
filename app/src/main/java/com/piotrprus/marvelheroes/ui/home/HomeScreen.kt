@file:OptIn(ExperimentalFoundationApi::class)

package com.piotrprus.marvelheroes.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import com.piotrprus.marvelheroes.feature.home.HomeViewModel
import com.piotrprus.marvelheroes.ui.model.CharacterItem

@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavController) {
    val lazyItems = viewModel.pagedList.collectAsLazyPagingItems()
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(lazyItems.itemCount) { key ->
            val cardModifier = Modifier
                .animateItemPlacement()
                .aspectRatio(1f)
                .fillMaxWidth()
            lazyItems[key]?.let {
                HeroCard(modifier = cardModifier, hero = it, onClick = {})
            } ?: PlaceholderHeroCard(cardModifier)
        }
    }
}

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
                                color = Color.Black
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

@Composable
fun PlaceholderHeroCard(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.placeholder(
            true,
            highlight = PlaceholderHighlight.shimmer(),
            color = MaterialTheme.colors.onPrimary
        )
    ) {
        Box {}
    }
}