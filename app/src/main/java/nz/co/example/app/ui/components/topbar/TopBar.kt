@file:OptIn(ExperimentalMaterial3Api::class)

package nz.co.example.app.ui.components.topbar

import android.content.res.Configuration
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nz.co.example.app.ui.components.BackButton
import nz.co.example.app.ui.theme.AppTheme

@Composable
internal fun TopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
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

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    AppTheme {
        Surface {
            TopAppBar(
                title = { Text("Title") },
                navigationIcon = { BackButton(onClick = {}) },
                actions = { Text("Actions") }
            )
        }
    }
}