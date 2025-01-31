package nz.co.example.app.features.navigation

import android.content.res.Configuration
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nz.co.example.app.features.navigation.models.FavouritesNavigation
import nz.co.example.app.features.navigation.models.topLevelNavigation
import nz.co.example.app.ui.theme.AppTheme

@Composable
internal fun BottomNavigation(currentDestination: String?, onItemClicked: (String) -> Unit) {
    Surface(shadowElevation = 12.dp, shape = RoundedCornerShape(8.dp, 8.dp, 0.dp, 0.dp)) {
        NavigationBar(
            containerColor = AppTheme.colors.background.bottomNavigation,
        ) {
            topLevelNavigation.forEach { topLevelRoute ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(topLevelRoute.icon),
                            contentDescription = stringResource(topLevelRoute.title)
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(topLevelRoute.title),
                            style = AppTheme.typography.navigation.bottomNavigation
                        )
                    },
                    selected = topLevelRoute.route == currentDestination,
                    onClick = { onItemClicked(topLevelRoute.route) },
                    colors = NavigationBarItemDefaults.colors().copy(
                        selectedIconColor = AppTheme.colors.accent.primary,
                        selectedTextColor = AppTheme.colors.accent.primary,
                        unselectedIconColor = AppTheme.colors.foreground.tertiary,
                        unselectedTextColor = AppTheme.colors.foreground.tertiary,
                        selectedIndicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    AppTheme {
        Surface {
            BottomNavigation(currentDestination = FavouritesNavigation.route, onItemClicked = {})
        }
    }
}