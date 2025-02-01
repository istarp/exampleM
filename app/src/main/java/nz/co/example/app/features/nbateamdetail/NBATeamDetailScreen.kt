package nz.co.example.app.features.nbateamdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nz.co.example.app.R
import nz.co.example.app.features.navigation.models.GenericNavigation
import nz.co.example.app.features.navigation.models.NavigationUp
import nz.co.example.app.features.nbateamdetail.models.UIONBATeamDetail
import nz.co.example.app.ui.components.BackButton
import nz.co.example.app.ui.components.topbar.TopAppBar
import nz.co.example.app.ui.lce.LCEState
import nz.co.example.app.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun NBATeamDetailScreen(
    modifier: Modifier = Modifier,
    teamId: String?,
    viewModel: NBATeamDetailViewModel = koinViewModel(parameters = { parametersOf(teamId) }),
    onNavigate: (GenericNavigation) -> Unit
) {
    val data by viewModel.data.collectAsStateWithLifecycle()

    Layout(modifier = modifier.fillMaxSize(), data = data, onNavigate = onNavigate)
}

@Composable
private fun Layout(
    data: LCEState<UIONBATeamDetail>,
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
        TeamDetailLCE(
            modifier = Modifier.windowInsetsPadding(WindowInsets.safeContent.only(WindowInsetsSides.Horizontal)),
            data = data
        )
    }
}

@Composable
private fun TeamDetailLCE(
    modifier: Modifier = Modifier,
    data: LCEState<UIONBATeamDetail>
) {
    when (data) {
        is LCEState.Loading -> Loading(modifier = modifier.fillMaxSize())
        is LCEState.Error -> Error(modifier = modifier.fillMaxSize())
        is LCEState.Content -> TeamDetail(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            model = data.value
        )
    }
}

@Composable
internal fun TeamDetail(
    modifier: Modifier = Modifier,
    model: UIONBATeamDetail
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Spacer(modifier = Modifier)
            DetailWithLabel(
                label = stringResource(R.string.nba_team_detail_full_name),
                value = model.fullName
            )
            DetailWithLabel(
                label = stringResource(R.string.nba_team_detail_abbreviation),
                value = model.abbreviation
            )
            Separator()
            DetailWithLabel(
                label = stringResource(R.string.nba_team_detail_division),
                value = model.division
            )
            DetailWithLabel(
                label = stringResource(R.string.nba_team_detail_conference),
                value = model.conference
            )
            Separator()
            DetailWithLabel(
                label = stringResource(R.string.nba_team_detail_city),
                value = model.city
            )
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

@PreviewLightDark
@Composable
private fun Preview() {
    AppTheme {
        Surface {
            Layout(
                data = LCEState.Content(UIONBATeamDetail.forPreview()),
                onNavigate = {})
        }
    }
}