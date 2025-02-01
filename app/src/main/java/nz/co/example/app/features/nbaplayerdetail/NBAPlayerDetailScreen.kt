@file:OptIn(ExperimentalMaterial3Api::class)

package nz.co.example.app.features.nbaplayerdetail

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nz.co.example.app.R
import nz.co.example.app.features.navigation.models.GenericNavigation
import nz.co.example.app.features.navigation.models.NavigationUp
import nz.co.example.app.features.nbaplayerdetail.model.UIONBAPlayerDetail
import nz.co.example.app.ui.components.BackButton
import nz.co.example.app.ui.components.topbar.TopAppBar
import nz.co.example.app.ui.lce.LCEState
import nz.co.example.app.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun NBAPlayerDetailScreen(
    modifier: Modifier = Modifier,
    playerId: String?,
    viewModel: NBAPlayerDetailViewModel = koinViewModel(parameters = { parametersOf(playerId) }),
    onNavigate: (GenericNavigation) -> Unit
) {
    val data by viewModel.data.collectAsStateWithLifecycle()

    Layout(modifier = modifier.fillMaxSize(), data = data, onNavigate = onNavigate)
}

@Composable
private fun Layout(
    data: LCEState<UIONBAPlayerDetail>,
    onNavigate: (GenericNavigation) -> Unit,
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
            },
            navigationIcon = {
                BackButton(onClick = { onNavigate(NavigationUp) })
            }
        )
        PlayerDetailLCE(
            modifier = Modifier.windowInsetsPadding(WindowInsets.safeContent.only(WindowInsetsSides.Horizontal)),
            data = data,
            onNavigate = onNavigate
        )
    }
}

@Composable
private fun PlayerDetailLCE(
    modifier: Modifier = Modifier,
    data: LCEState<UIONBAPlayerDetail>,
    onNavigate: (GenericNavigation) -> Unit
) {
    when (data) {
        is LCEState.Loading -> Loading(modifier = modifier.fillMaxSize())
        is LCEState.Error -> Error(modifier = modifier.fillMaxSize())
        is LCEState.Content -> PlayerDetail(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            model = data.value,
            onNavigate = onNavigate
        )
    }
}

@Composable
internal fun PlayerDetail(
    modifier: Modifier = Modifier,
    model: UIONBAPlayerDetail,
    onNavigate: (GenericNavigation) -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.background.tertiary),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier)
            DetailWithLabel(
                modifier = Modifier.padding(horizontal = 16.dp),
                label = stringResource(R.string.nba_player_detail_height),
                value = model.height
            )
            DetailWithLabel(
                modifier = Modifier.padding(horizontal = 16.dp),
                label = stringResource(R.string.nba_player_detail_weight), value = model.weight
            )
            Separator()
            DetailWithLabel(
                modifier = Modifier.padding(horizontal = 16.dp),
                label = stringResource(R.string.nba_player_detail_college), value = model.college
            )
            DetailWithLabel(
                modifier = Modifier.padding(horizontal = 16.dp),
                label = stringResource(R.string.nba_player_detail_country), value = model.country
            )
            Separator()
            DetailWithLabel(
                modifier = Modifier.padding(horizontal = 16.dp),
                label = stringResource(R.string.nba_player_detail_draft_year), value = model.draftYear
            )
            DetailWithLabel(
                modifier = Modifier.padding(horizontal = 16.dp),
                label = stringResource(R.string.nba_player_detail_draft_number), value = model.draftNumber
            )
            DetailWithLabel(
                modifier = Modifier.padding(horizontal = 16.dp),
                label = stringResource(R.string.nba_player_detail_draft_round), value = model.draftRound
            )
            Separator()
            NavigationDetailWithLabel(
                modifier = Modifier.padding(horizontal = 16.dp),
                label = stringResource(R.string.nba_player_detail_team),
                value = model.teamName,
                onClick = {})
            DetailWithLabel(
                modifier = Modifier.padding(horizontal = 16.dp),
                label = stringResource(R.string.nba_player_detail_position),
                value = model.position
            )
            Spacer(modifier = Modifier)
        }
    }
}

@Composable
private fun Separator(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(AppTheme.colors.foreground.separator)
    )
}

@Composable
private fun DetailWithLabel(modifier: Modifier = Modifier, label: String, value: String) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = AppTheme.typography.paragraph.small,
            color = AppTheme.colors.foreground.secondary
        )
        Text(
            text = value.ifBlank { "-" },
            style = AppTheme.typography.headline.headline3,
            color = AppTheme.colors.foreground.primary
        )
    }
}

@Composable
private fun NavigationDetailWithLabel(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onClick: () -> Unit
) {
    Row(modifier = Modifier
        .clickable { onClick() }
        .then(modifier), verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = AppTheme.typography.paragraph.small,
                color = AppTheme.colors.foreground.secondary
            )
            Text(
                text = value.ifBlank { "-" },
                style = AppTheme.typography.headline.headline3,
                color = AppTheme.colors.foreground.primary
            )
        }
        Icon(
            modifier = Modifier.size(24.dp),
            tint = AppTheme.colors.foreground.primary,
            painter = painterResource(R.drawable.ic_nav_forward),
            contentDescription = stringResource(R.string.content_desc_nav_forward)
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
                data = LCEState.Content(UIONBAPlayerDetail.forPreview()),
                onNavigate = {})
        }
    }
}