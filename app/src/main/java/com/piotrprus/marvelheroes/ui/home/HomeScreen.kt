@file:OptIn(ExperimentalFoundationApi::class)

package com.piotrprus.marvelheroes.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import com.piotrprus.marvelheroes.feature.home.HomeViewModel
import com.piotrprus.marvelheroes.ui.MainScreen
import com.piotrprus.marvelheroes.ui.Screen
import com.piotrprus.marvelheroes.ui.common.HeroCard

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
                HeroCard(modifier = cardModifier, hero = it, onClick = { heroId ->
                    navController.navigate(
                        Screen.HeroDetail.createRoute(MainScreen.Home, heroId)
                    )
                })
            } ?: PlaceholderHeroCard(cardModifier)
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