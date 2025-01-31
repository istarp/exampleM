package nz.co.example.app.ui.components.charactercard

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nz.co.example.app.R
import nz.co.example.app.ui.components.charactercard.model.UIOCharacterCard
import nz.co.example.app.ui.components.image.Image
import nz.co.example.app.ui.theme.AppTheme

@Composable
internal fun CharacterCard(
    modifier: Modifier = Modifier,
    model: UIOCharacterCard,
    onClick: (UIOCharacterCard) -> Unit
) {
    Card(
        modifier = Modifier,
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.background.tertiary),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = modifier
                .clickable { onClick(model) }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(8.dp)),
                imageUrl = model.imageUrl
            )
            Box(modifier = Modifier.width(8.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier.weight(1f, false),
                        text = model.name,
                        style = AppTheme.typography.headline.headline3,
                        color = AppTheme.colors.foreground.primary
                    )
                    if (model.isFavourite) {
                        Box(modifier = Modifier.width(8.dp))
                        Icon(
                            modifier = Modifier.size(16.dp),
                            painter = painterResource(R.drawable.ic_favourite),
                            contentDescription = stringResource(R.string.content_desc_favourite),
                            tint = AppTheme.colors.accent.primary
                        )
                    }
                }
                Text(
                    text = model.status,
                    style = AppTheme.typography.paragraph.small,
                    color = AppTheme.colors.foreground.secondary
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
            CharacterCard(model = UIOCharacterCard.forPreview(), onClick = {})
        }
    }
}