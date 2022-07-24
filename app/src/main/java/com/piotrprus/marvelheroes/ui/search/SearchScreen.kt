@file:OptIn(ExperimentalFoundationApi::class)

package com.piotrprus.marvelheroes.ui.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import com.piotrprus.marvelheroes.R
import com.piotrprus.marvelheroes.data.model.CharacterItem
import com.piotrprus.marvelheroes.feature.search.SearchViewModel
import com.piotrprus.marvelheroes.ui.MainScreen
import com.piotrprus.marvelheroes.ui.Screen

@Composable
fun SearchScreen(viewModel: SearchViewModel, navController: NavController) {
    fun navigateToDetail(heroId: Int) {
        navController.navigate(
            Screen.HeroDetail.createRoute(MainScreen.Search, heroId)
        )
    }

    val state by viewModel.state.collectAsState()
    val scaffoldState = rememberScaffoldState()

    state.uiMessage?.let { message ->
        LaunchedEffect(message) {
            scaffoldState.snackbarHostState.showSnackbar(message.message)
            viewModel.clearMessage(message.id)
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            Surface(
                color = MaterialTheme.colors.surface.copy(alpha = 0.95f),
                contentColor = MaterialTheme.colors.onSurface,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .statusBarsPadding()
                        .fillMaxWidth()
                ) {
                    var searchQuery by remember { mutableStateOf(TextFieldValue(state.searchQuery)) }
                    SearchTextField(
                        value = searchQuery,
                        onValueChange = { value ->
                            searchQuery = value
                            viewModel.search(value.text)
                        },
                        hint = stringResource(id = R.string.search_hint),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        },
        snackbarHost = { snackbarHostState ->
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.list) { item ->
                SearchHeroItem(
                    modifier = Modifier
                        .animateItemPlacement()
                        .fillMaxWidth(),
                    hero = item,
                    onItemClick = { navigateToDetail(heroId = it) })
            }
        }
    }
}

@Composable
fun SearchHeroItem(modifier: Modifier, hero: CharacterItem, onItemClick: (Int) -> Unit) {
    Row(
        modifier = modifier
            .padding(8.dp)
            .clickable { onItemClick(hero.id) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .aspectRatio(1f),
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
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = hero.name, style = MaterialTheme.typography.button)
    }
}

@Composable
fun SearchTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions()
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        trailingIcon = {
            AnimatedVisibility(
                visible = value.text.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                IconButton(
                    onClick = { onValueChange(TextFieldValue()) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "clear icon"
                    )
                }
            }
        },
        placeholder = { Text(text = hint) },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        maxLines = 1,
        singleLine = true,
        modifier = modifier
    )
}