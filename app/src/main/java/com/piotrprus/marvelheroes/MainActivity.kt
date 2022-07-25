@file:OptIn(ExperimentalAnimationApi::class)

package com.piotrprus.marvelheroes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.piotrprus.marvelheroes.ui.MainScreen
import com.piotrprus.marvelheroes.ui.Screen
import com.piotrprus.marvelheroes.ui.detail.DetailScreen
import com.piotrprus.marvelheroes.ui.favourite.FavouriteScreen
import com.piotrprus.marvelheroes.ui.home.HomeScreen
import com.piotrprus.marvelheroes.ui.search.SearchScreen
import com.piotrprus.marvelheroes.ui.theme.MarvelHeroesTheme
import org.koin.androidx.compose.getViewModel
import timber.log.Timber

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelHeroesTheme {
                HeroesApp()
            }
        }
    }
}

@Composable
fun HeroesApp() {
    val navController = rememberAnimatedNavController()
    Scaffold(bottomBar = {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        if (currentDestination?.route != null) {
            val currentSelectedItem by navController.currentScreenAsState()
            BottomNavigation {
                listOf(
                    MainScreen.Home,
                    MainScreen.Favourites,
                    MainScreen.Search
                ).forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(stringResource(screen.resourceId)) },
                        selected = currentSelectedItem == screen,
                        onClick = {
                            if (currentDestination.route?.substringBefore("/") != screen.mainRoute) {
                                navController.navigate(screen.mainRoute) {
                                    launchSingleTop = true
                                    restoreState = true

                                    popUpTo(Screen.Home.createRoute(MainScreen.Home)) {
                                        saveState = true
                                    }
                                }
                            }
                        }
                    )
                }
            }

        }
    }) { paddingValues ->
        AnimatedNavHost(
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
            navController = navController,
            startDestination = MainScreen.Home.mainRoute
        ) {
            navigation(
                route = MainScreen.Home.mainRoute,
                startDestination = Screen.Home.createRoute(MainScreen.Home)
            ) {
                composable(route = Screen.Home.createRoute(MainScreen.Home)) {
                    HomeScreen(viewModel = getViewModel()) { heroId ->
                        navController.navigate(
                            Screen.HeroDetail.createRoute(MainScreen.Home, heroId)
                        )
                    }
                }
                composable(
                    route = Screen.HeroDetail.createRoute(MainScreen.Home),
                    arguments = Screen.HeroDetail.arguments
                ) { navBackStackEntry ->
                    Screen.HeroDetail.parameters(navBackStackEntry.arguments)
                        ?.let { parametersHolder ->
                            DetailScreen(
                                viewModel = getViewModel { parametersHolder }) {
                                navController.navigateUp()
                            }
                        } ?: Timber.w("Cannot navigate to hero detail screen")
                }
            }
            navigation(
                route = MainScreen.Favourites.mainRoute,
                startDestination = Screen.Favourites.createRoute(MainScreen.Favourites)
            ) {
                composable(route = Screen.Favourites.createRoute(MainScreen.Favourites)) {
                    FavouriteScreen(viewModel = getViewModel()) { heroId ->
                        navController.navigate(
                            Screen.HeroDetail.createRoute(
                                MainScreen.Favourites,
                                heroId
                            )
                        )
                    }
                }
                composable(
                    route = Screen.HeroDetail.createRoute(MainScreen.Favourites),
                    arguments = Screen.HeroDetail.arguments
                ) { navBackStackEntry ->
                    Screen.HeroDetail.parameters(navBackStackEntry.arguments)
                        ?.let { parametersHolder ->
                            DetailScreen(
                                viewModel = getViewModel { parametersHolder }
                            ) { navController.navigateUp() }
                        } ?: Timber.w("Cannot navigate to hero detail screen")
                }
            }
            navigation(
                route = MainScreen.Search.mainRoute,
                startDestination = Screen.Search.createRoute(MainScreen.Search)
            ) {
                composable(route = Screen.Search.createRoute(MainScreen.Search)) {
                    SearchScreen(viewModel = getViewModel()) { heroId ->
                        navController.navigate(
                            Screen.HeroDetail.createRoute(MainScreen.Search, heroId)
                        )
                    }
                }
                composable(
                    route = Screen.HeroDetail.createRoute(MainScreen.Search),
                    arguments = Screen.HeroDetail.arguments
                ) { navBackStackEntry ->
                    Screen.HeroDetail.parameters(navBackStackEntry.arguments)
                        ?.let { parametersHolder ->
                            DetailScreen(
                                viewModel = getViewModel { parametersHolder }) {
                                navController.navigateUp()
                            }
                        } ?: Timber.w("Cannot navigate to hero detail screen")
                }
            }

        }
    }
}

@Composable
private fun NavController.currentScreenAsState(): State<MainScreen> {
    val selectedItem = remember { mutableStateOf<MainScreen>(MainScreen.Home) }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when {
                destination.hierarchy.any { it.route == MainScreen.Home.mainRoute } -> {
                    selectedItem.value = MainScreen.Home
                }
                destination.hierarchy.any { it.route == MainScreen.Favourites.mainRoute } -> {
                    selectedItem.value = MainScreen.Favourites
                }
                destination.hierarchy.any { it.route == MainScreen.Search.mainRoute } -> {
                    selectedItem.value = MainScreen.Search
                }
            }
        }
        addOnDestinationChangedListener(listener)

        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }

    return selectedItem
}