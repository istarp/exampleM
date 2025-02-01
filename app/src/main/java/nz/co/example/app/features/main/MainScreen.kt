package nz.co.example.app.features.main

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import nz.co.example.app.features.navigation.NavigationScreen
import nz.co.example.app.features.navigation.models.AppNavigationRoute
import nz.co.example.app.ui.theme.AppTheme

@Composable
internal fun MainScreen(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    AppTheme {
        Scaffold(
            containerColor = AppTheme.colors.background.primary,
            modifier = modifier,
            bottomBar = {}
        ) { innerPadding ->
            MainLayout(
                startDestination = AppNavigationRoute.NBAPlayers.route,
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