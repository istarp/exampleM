package nz.co.example.app.features.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import nz.co.example.app.features.navigation.BottomNavigation
import nz.co.example.app.features.navigation.DEFAULT_TRANSITION_DURATION_IN_MILLIS
import nz.co.example.app.features.navigation.NavigationScreen
import nz.co.example.app.features.navigation.models.CharactersNavigation
import nz.co.example.app.features.navigation.models.topLevelRoutes
import nz.co.example.app.ui.theme.AppTheme

@Composable
internal fun MainScreen(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    AppTheme {
        Scaffold(
            containerColor = AppTheme.colors.background.primary,
            modifier = modifier,
            bottomBar = {
                AnimatedVisibility(
                    visible = topLevelRoutes.contains(currentDestination?.route),
                    enter = slideInVertically(animationSpec = tween(DEFAULT_TRANSITION_DURATION_IN_MILLIS)) { fullHeight -> fullHeight },
                    exit = slideOutVertically(animationSpec = tween(DEFAULT_TRANSITION_DURATION_IN_MILLIS)) { fullHeight -> fullHeight }
                ) {
                    BottomNavigation(
                        currentDestination = currentDestination?.route,
                        onItemClicked = {
                            navController.navigate(it) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        })
                }
            }
        ) { innerPadding ->
            MainLayout(
                startDestination = CharactersNavigation.route,
                navController = navController,
                modifier = Modifier.padding(
                    bottom = innerPadding.calculateBottomPadding(),
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current)
                )
            )
        }
    }
}

@Composable
private fun MainLayout(
    startDestination: String,
    navController: NavHostController,
    modifier: Modifier
) {
    NavigationScreen(
        startDestination = startDestination,
        navController = navController,
        modifier = modifier
    )
}