package com.piotrprus.marvelheroes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.piotrprus.marvelheroes.feature.home.HomeViewModel
import com.piotrprus.marvelheroes.ui.theme.MarvelHeroesTheme
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelHeroesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SimpleTextList()
                }
            }
        }
    }
}

@Composable
fun SimpleTextList() {
    val viewModel = getViewModel<HomeViewModel>()
    val lazyItems = viewModel.pagedList.collectAsLazyPagingItems()
    LazyColumn {
        items(lazyItems) { hero ->
            hero?.let {
                Text(text = "This is ${it.name}")
            }
        }
    }
}