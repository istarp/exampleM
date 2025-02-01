@file:OptIn(ExperimentalMaterial3Api::class)

package nz.co.example.app.ui.components.topbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nz.co.example.app.ui.components.BackButton
import nz.co.example.app.ui.theme.AppTheme

@Composable
internal fun TopAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    Surface(shadowElevation = 12.dp) {
        androidx.compose.material3.TopAppBar(
            modifier = modifier,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = AppTheme.colors.background.primary,
                scrolledContainerColor = Color.Transparent
            ),
            expandedHeight = 48.dp,
            title = { title() },
            navigationIcon = { navigationIcon() },
            actions = { actions() }
        )
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    AppTheme {
        Surface {
            TopAppBar(
                title = { Text("Title", color = AppTheme.colors.foreground.primary) },
                navigationIcon = { BackButton(onClick = {}) },
                actions = { Text("Actions") }
            )
        }
    }
}