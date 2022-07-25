@file:OptIn(ExperimentalFoundationApi::class)

package com.piotrprus.marvelheroes.ui.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import com.piotrprus.marvelheroes.R
import com.piotrprus.marvelheroes.feature.home.HomeViewModel
import com.piotrprus.marvelheroes.ui.MainScreen
import com.piotrprus.marvelheroes.ui.Screen
import com.piotrprus.marvelheroes.ui.common.HeroCard
import com.piotrprus.marvelheroes.ui.common.appendErrorOrNull
import com.piotrprus.marvelheroes.ui.common.prependErrorOrNull
import com.piotrprus.marvelheroes.ui.common.refreshErrorOrNull

@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavController) {
    val lazyItems = viewModel.pagedList.collectAsLazyPagingItems()
    val scaffoldState = rememberScaffoldState()

    Scaffold(scaffoldState = scaffoldState) { paddingValues ->
        if (lazyItems.loadState.refresh == LoadState.Loading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val infiniteTransition = rememberInfiniteTransition()
                val animatedSize = infiniteTransition.animateFloat(
                    initialValue = 0.8f,
                    targetValue = 1.1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(800, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse
                    )
                )
                Icon(
                    modifier = Modifier
                        .padding(12.dp)
                        .width(60.dp)
                        .scale(animatedSize.value),
                    painter = painterResource(id = R.drawable.ic_superhero),
                    contentDescription = "superhero icon",
                    tint = MaterialTheme.colors.onSurface
                )
                Text(text = stringResource(R.string.home_hero_preload))
            }
        }

        lazyItems.loadState.prependErrorOrNull()?.let { message ->
            LaunchedEffect(message) {
                scaffoldState.snackbarHostState.showSnackbar(message.message)
            }
        }
        lazyItems.loadState.appendErrorOrNull()?.let { message ->
            LaunchedEffect(message) {
                scaffoldState.snackbarHostState.showSnackbar(message.message)
            }
        }
        lazyItems.loadState.refreshErrorOrNull()?.let { message ->
            LaunchedEffect(message) {
                scaffoldState.snackbarHostState.showSnackbar(message.message)
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
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
            if (lazyItems.loadState.append == LoadState.Loading) {
                item(
                    key = null,
                    span = { GridItemSpan(maxLineSpan) },
                    contentType = null
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                }
            }
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