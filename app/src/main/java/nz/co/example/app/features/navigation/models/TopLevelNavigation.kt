package nz.co.example.app.features.navigation.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import nz.co.example.app.R

data class TopLevelNavigation(@StringRes val title: Int, val route: String, @DrawableRes val icon: Int)

val CharactersNavigation =
    TopLevelNavigation(
        title = R.string.nav_characters,
        route = AppNavigationRoute.Characters.route,
        icon = R.drawable.icon_characters
    )
val FavouritesNavigation = TopLevelNavigation(
    title = R.string.nav_favourites,
    route = AppNavigationRoute.Favourites.route,
    icon = R.drawable.ic_nav_favourites
)

val topLevelNavigation = listOf(
    CharactersNavigation, FavouritesNavigation
)

val topLevelRoutes = topLevelNavigation.map { it.route }