package com.piotrprus.marvelheroes.ui

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.piotrprus.marvelheroes.R
import org.koin.core.parameter.ParametersHolder
import org.koin.core.parameter.parametersOf

sealed class MainScreen(
    val mainRoute: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector
) {
    object Home : MainScreen(
        "home",
        R.string.main_screen_home,
        Icons.Default.Home
    )

    object Favourites : MainScreen(
        "favourites",
        R.string.main_screen_favourites,
        Icons.Default.Favorite
    )

    object Search : MainScreen(
        "search",
        R.string.main_screen_search,
        Icons.Default.Search
    )
}

sealed class Screen(val route: String) {
    fun createRoute(root: MainScreen) = "${root.mainRoute}/$route"

    object HeroDetail :
        Screen("hero/{heroId}") {
        val arguments = listOf(
            navArgument("heroId") { type = NavType.IntType }
        )

        fun createRoute(root: MainScreen, heroId: Int) =
            "${root.mainRoute}/hero/$heroId"

        fun parameters(arguments: Bundle?): ParametersHolder? {
            return arguments?.getInt("heroId")?.let { parametersOf(it) }
        }
    }
}
