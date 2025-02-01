package nz.co.example.app.features.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import nz.co.example.app.features.nbaplayerdetail.NBAPlayerDetailScreen
import nz.co.example.app.features.nbaplayers.NBAPlayersScreen
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
        composable(AppNavigationRoute.NBAPlayers.route) {
            NBAPlayersScreen(
                modifier = modifier,
                onNavigate = { handleNavigation(it, navController) }
            )
        }
        composable(AppNavigationRoute.NBAPlayerDetail().route) { entry ->
            NBAPlayerDetailScreen(
                modifier = modifier,
                playerId = AppNavigationRoute.NBAPlayerDetail.getArg(entry),
                onNavigate = { handleNavigation(it, navController) })
        }
    }
}

private fun handleNavigation(navigation: GenericNavigation, navController: NavHostController) {
    when (navigation) {
        NavigationUp -> navController.navigateUp()
        is RouteNavigation -> navController.navigate(navigation.value)
    }
}