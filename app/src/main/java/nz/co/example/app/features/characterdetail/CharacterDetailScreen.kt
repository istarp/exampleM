@file:OptIn(ExperimentalMaterial3Api::class)

package nz.co.example.app.features.characterdetail

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nz.co.example.app.R
import nz.co.example.app.features.characterdetail.model.UIOCharacterDetail
import nz.co.example.app.features.navigation.models.GenericNavigation
import nz.co.example.app.features.navigation.models.NavigationUp
import nz.co.example.app.ui.components.BackButton
import nz.co.example.app.ui.components.image.Image
import nz.co.example.app.ui.components.topbar.TopAppBar
import nz.co.example.app.ui.lce.LCEState
import nz.co.example.app.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun CharacterDetailScreen(
    modifier: Modifier = Modifier,
    characterId: String?,
    viewModel: CharacterDetailViewModel = koinViewModel(parameters = { parametersOf(characterId) }),
    onNavigate: (GenericNavigation) -> Unit
) {
    val data by viewModel.data.collectAsStateWithLifecycle()

    Box(modifier = modifier.fillMaxSize()) {
        Layout(data = data, onNavigate = onNavigate, onToggleFavourite = viewModel::onToggleFavourite)
    }
}

@Composable
private fun Layout(
    data: LCEState<UIOCharacterDetail>,
    onNavigate: (GenericNavigation) -> Unit,
    onToggleFavourite: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        TopAppBar(
            modifier = Modifier,

            title = {
                Text(
                    text = (data as? LCEState.Content)?.value?.name ?: "",
                    style = AppTheme.typography.headline.headline2,
                    color = AppTheme.colors.foreground.primary,
                    overflow = TextOverflow.Ellipsis
                )
            }, navigationIcon = {
                BackButton(onClick = { onNavigate(NavigationUp) })
            },
            actions = {
                when (data) {
                    is LCEState.Content -> {
                        IconButton(modifier = modifier, onClick = { onToggleFavourite() }) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(if (data.value.isFavourite) R.drawable.ic_favourite else R.drawable.ic_not_favourite),
                                contentDescription = stringResource(if (data.value.isFavourite) R.string.content_desc_set_as_not_favourite else R.string.content_desc_set_as_favourite),
                                tint = if (data.value.isFavourite) AppTheme.colors.accent.primary else AppTheme.colors.icon.primary
                            )
                        }
                    }

                    is LCEState.Loading, is LCEState.Error -> {}
                }
            }
        )
        when (data) {
            is LCEState.Content -> CharacterDetail(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                model = data.value
            )

            is LCEState.Error -> Error(modifier = Modifier.fillMaxSize())
            is LCEState.Loading -> Loading(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
internal fun CharacterDetail(
    modifier: Modifier = Modifier,
    model: UIOCharacterDetail
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.background.tertiary),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())) {
            Row(
                modifier = Modifier.padding(16.dp)
            ) {
                Image(
                    modifier = Modifier
                        .size(140.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    imageUrl = model.imageUrl
                )
                Box(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = stringResource(R.string.character_detail_name),
                        style = AppTheme.typography.paragraph.medium,
                        color = AppTheme.colors.foreground.secondary
                    )
                    Box(modifier = Modifier.height(16.dp))
                    Text(
                        text = model.name,
                        style = AppTheme.typography.headline.headline2,
                        color = AppTheme.colors.foreground.primary
                    )
                }
            }
            Box(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(AppTheme.colors.foreground.separator)
            )
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                DetailItem(label = stringResource(R.string.character_detail_status), value = model.status)
                DetailItem(label = stringResource(R.string.character_detail_species), value = model.species)
                DetailItem(label = stringResource(R.string.character_detail_type), value = model.type)
                DetailItem(label = stringResource(R.string.character_detail_gender), value = model.gender)
                DetailItem(label = stringResource(R.string.character_detail_origin), value = model.origin)
                DetailItem(label = stringResource(R.string.character_detail_location), value = model.location)
            }
        }
    }
}

@Composable
private fun DetailItem(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = AppTheme.typography.paragraph.small,
            color = AppTheme.colors.foreground.secondary
        )
        Text(
            text = value,
            style = AppTheme.typography.headline.headline3,
            color = AppTheme.colors.foreground.primary
        )
    }
}

@Composable
private fun Loading(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Text(stringResource(R.string.loading_data), textAlign = TextAlign.Center)
    }
}

@Composable
private fun Error(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Text(stringResource(R.string.error), textAlign = TextAlign.Center)
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    AppTheme {
        Surface {
            Layout(
                data = LCEState.Content(UIOCharacterDetail.forPreview().copy(isFavourite = false)),
                onToggleFavourite = {},
                onNavigate = {})
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewFavourite() {
    AppTheme {
        Surface {
            Layout(
                data = LCEState.Content(UIOCharacterDetail.forPreview()),
                onToggleFavourite = {},
                onNavigate = {})
        }
    }
}

