@file:OptIn(ExperimentalFoundationApi::class)

package com.piotrprus.marvelheroes.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.piotrprus.marvelheroes.data.model.FavouriteItem
import com.piotrprus.marvelheroes.feature.favourite.FavouriteViewModel
import com.piotrprus.marvelheroes.ui.common.HeroCard

@Composable
fun FavouriteScreen(viewModel: FavouriteViewModel, navController: NavController) {
    when (val state = viewModel.state.collectAsState().value) {
        null -> InitialScreen()
        emptyList<FavouriteItem>() -> EmptyScreen()
        else -> FavouriteScreenContent(
            state,
            onFavClick = { heroId ->
                navController.navigate(
                    Screen.HeroDetail.createRoute(
                        MainScreen.Favourites,
                        heroId
                    )
                )
            })
    }
}

@Composable
private fun FavouriteScreenContent(state: List<FavouriteItem>, onFavClick: (Int) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(items = state) {
            val cardModifier = Modifier
                .animateItemPlacement()
                .aspectRatio(1f)
                .fillMaxWidth()
            HeroCard(modifier = cardModifier, hero = it.info, onClick = { heroId ->
                onFavClick(heroId)
            })
        }
    }
}

@Composable
private fun EmptyScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorite icon")
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier.padding(horizontal = 40.dp),
            text = "Add some favourites from home screen",
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Composable
private fun InitialScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Data in preparation...")
    }
}