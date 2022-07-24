package com.piotrprus.marvelheroes.ui.detail

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import com.piotrprus.marvelheroes.R
import com.piotrprus.marvelheroes.data.model.CharacterItem
import com.piotrprus.marvelheroes.data.model.ThumbnailItem
import com.piotrprus.marvelheroes.feature.detail.DetailViewModel
import com.piotrprus.marvelheroes.ui.common.BoxPlaceholder

@Composable
fun DetailScreen(viewModel: DetailViewModel, navController: NavController) {
    val state = viewModel.state.collectAsState().value
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = state.info?.name ?: "") }, navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Arrow back icon")
            }
        })
    }) { paddingValues ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(bottom = paddingValues.calculateBottomPadding())
                .verticalScroll(scrollState)
        ) {
            state.info?.let {
                HeroInfo(
                    hero = it,
                    isFavourite = state.isFavourite,
                    onFavClicked = { viewModel.onFavouriteClick() })
            } ?: InfoPlaceholder()
            state.comics?.let {
                ThumbnailReel(
                    title = stringResource(R.string.detail_comics_title),
                    emptyViewDescription = stringResource(R.string.detail_comics_empty_message),
                    list = it
                )
            } ?: ThumbnailReelPlaceholder()
            state.events?.let {
                ThumbnailReel(
                    title = stringResource(R.string.detail_events_title),
                    emptyViewDescription = stringResource(R.string.detail_events_empty_message),
                    list = it
                )
            } ?: ThumbnailReelPlaceholder()
        }
    }
}

@Composable
fun InfoPlaceholder() {
    BoxPlaceholder(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        BoxPlaceholder(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.width(16.dp))
        BoxPlaceholder(modifier = Modifier.size(40.dp))
    }
    Spacer(modifier = Modifier.height(16.dp))
    BoxPlaceholder(
        modifier = Modifier
            .fillMaxWidth()
            .height(16.dp)
    )
    Spacer(modifier = Modifier.height(16.dp))
    BoxPlaceholder(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    )
}

@Composable
fun ThumbnailReelPlaceholder() {
    BoxPlaceholder(
        modifier = Modifier
            .fillMaxWidth()
            .height(16.dp)
    )
    Spacer(modifier = Modifier.height(16.dp))
    BoxPlaceholder(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}

@Composable
fun ColumnScope.HeroInfo(hero: CharacterItem, isFavourite: Boolean, onFavClicked: () -> Unit) {
    SubcomposeAsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
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
        contentScale = ContentScale.FillWidth
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = hero.name, style = MaterialTheme.typography.h5, modifier = Modifier.weight(1f))
        FloatingActionButton(onClick = { onFavClicked() }) {
            Icon(
                imageVector = if (isFavourite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = "Favourite icon"
            )
        }
    }
    if (hero.description.isNotEmpty()) {
        Text(
            text = "Description",
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        Divider(
            Modifier
                .fillMaxWidth(0.9f)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = hero.description,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun ColumnScope.ThumbnailReel(
    title: String,
    emptyViewDescription: String,
    list: List<ThumbnailItem>
) {
    Text(
        text = title,
        style = MaterialTheme.typography.caption,
        modifier = Modifier.padding(horizontal = 12.dp)
    )
    Divider(
        Modifier
            .fillMaxWidth(0.9f)
            .align(Alignment.CenterHorizontally)
    )
    if (list.isEmpty()) {
        Text(
            text = emptyViewDescription,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 24.dp)
        )
    } else {
        val scrollState = rememberScrollState()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .horizontalScroll(scrollState)
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            list.forEach { thumbnail ->
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .height(200.dp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(thumbnail.imageUrl)
                        .crossfade(true)
                        .build(), contentDescription = "",
                    loading = {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .aspectRatio(2 / 3f)
                                .placeholder(
                                    true,
                                    highlight = PlaceholderHighlight.shimmer(),
                                    color = MaterialTheme.colors.onSurface
                                )
                        )
                    },
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}