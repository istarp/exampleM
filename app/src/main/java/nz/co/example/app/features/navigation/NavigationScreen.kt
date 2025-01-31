package nz.co.example.app.features.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import nz.co.example.app.features.characterdetail.CharacterDetailScreen
import nz.co.example.app.features.characters.CharactersScreen
import nz.co.example.app.features.favouritecharacters.FavouriteCharactersScreen
import nz.co.example.app.features.navigation.models.AppNavigationRoute
import nz.co.example.app.features.navigation.models.GenericNavigation
import nz.co.example.app.features.navigation.models.NavigationUp
import nz.co.example.app.features.navigation.models.RouteNavigation

@Composable
internal fun NavigationScreen(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = navEnterTransition(),
        exitTransition = navExitTransition(),
        popEnterTransition = navPopEnterTransition(),
        popExitTransition = navPopExitTransition(),
        modifier = Modifier
    ) {
        composable(AppNavigationRoute.Characters.route) {
            CharactersScreen(
                modifier = modifier,
                onNavigate = { handleNavigation(it, navController) }
            )
        }
        composable(AppNavigationRoute.CharacterDetail().route) { entry ->
            CharacterDetailScreen(
                modifier = modifier,
                characterId = AppNavigationRoute.CharacterDetail.getArg(entry),
                onNavigate = { handleNavigation(it, navController) })
        }
        composable(AppNavigationRoute.Favourites.route) {
            FavouriteCharactersScreen(
                modifier = modifier,
                onNavigate = { handleNavigation(it, navController) }
            )
        }
    }
}

private fun handleNavigation(navigation: GenericNavigation, navController: NavHostController) {
    when (navigation) {
        NavigationUp -> navController.navigateUp()
        is RouteNavigation -> navController.navigate(navigation.value)
    }
}