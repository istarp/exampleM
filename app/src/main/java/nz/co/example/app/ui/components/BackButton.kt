package nz.co.example.app.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nz.co.example.app.R
import nz.co.example.app.ui.theme.AppTheme

@Composable
internal fun BackButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(modifier = modifier, onClick = onClick) {
        Icon(
            modifier = Modifier.size(24.dp),
            tint = AppTheme.colors.foreground.primary,
            painter = painterResource(R.drawable.ic_nav_back),
            contentDescription = stringResource(R.string.content_desc_nav_back)
        )
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    AppTheme {
        Surface {
            BackButton(onClick = {})
        }
    }
}
