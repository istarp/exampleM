@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package nz.co.example.app.features.nbaplayers

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import kotlinx.coroutines.flow.MutableStateFlow
import nz.co.example.app.R
import nz.co.example.app.features.navigation.models.AppNavigationRoute
import nz.co.example.app.features.navigation.models.GenericNavigation
import nz.co.example.app.features.navigation.models.RouteNavigation
import nz.co.example.app.features.nbaplayers.model.UIONBAPlayerCard
import nz.co.example.app.ui.components.topbar.TopAppBar
import nz.co.example.app.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun NBAPlayersScreen(
    modifier: Modifier = Modifier,
    onNavigate: (GenericNavigation) -> Unit,
    viewModel: NBAPlayersViewModel = koinViewModel()
) {
    val players = viewModel.data.collectAsLazyPagingItems()

    Box(modifier = modifier) {
        Layout(
            players = players,
            onNavigate = onNavigate,
        )
    }
}

@Composable
private fun Layout(
    players: LazyPagingItems<UIONBAPlayerCard>,
    onNavigate: (GenericNavigation) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        TopAppBar(
            title = {
                Box(contentAlignment = Alignment.CenterStart) {
                    Text(
                        text = stringResource(R.string.nba_players_title),
                        maxLines = 1,
                        style = AppTheme.typography.headline.headline2,
                        color = AppTheme.colors.foreground.primary,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        )
        NBAPlayersLCE(players = players, onNavigate = onNavigate)
    }
}

@Composable
private fun NBAPlayersLCE(
    players: LazyPagingItems<UIONBAPlayerCard>,
    onNavigate: (GenericNavigation) -> Unit,
    modifier: Modifier = Modifier
) = when {
    players.loadState.refresh is LoadState.Loading && players.itemCount == 0 -> {
        Loading(modifier = Modifier.fillMaxSize())
    }

    players.loadState.refresh is LoadState.Error -> {
        Error(modifier = Modifier.fillMaxSize())
    }

    else -> {
        Players(modifier, players, onNavigate)
    }
}

@Composable
private fun Players(
    modifier: Modifier,
    players: LazyPagingItems<UIONBAPlayerCard>,
    onNavigate: (GenericNavigation) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(count = players.itemCount, key = players.itemKey { it.id }) { index ->
            players[index]?.let {
                PlayerCard(
                    modifier = Modifier.fillMaxWidth(),
                    model = it,
                    onClick = { card ->
                        onNavigate(RouteNavigation(AppNavigationRoute.NBAPlayerDetail.createRoute(card.id.toString())))
                    }
                )
            }
        }
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

@Composable
internal fun PlayerCard(
    modifier: Modifier = Modifier,
    model: UIONBAPlayerCard,
    onClick: (UIONBAPlayerCard) -> Unit
) {
    Card(
        modifier = Modifier,
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.background.tertiary),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = modifier
                .clickable { onClick(model) }
                .padding(16.dp),
        ) {
            Text(
                text = model.name,
                style = AppTheme.typography.headline.headline3,
                color = AppTheme.colors.foreground.primary
            )
            DetailWithLabel(label = stringResource(R.string.nba_players_team), detail = model.team)
            DetailWithLabel(label = stringResource(R.string.nba_players_position), detail = model.position)
        }
    }
}

@Composable
private fun DetailWithLabel(modifier: Modifier = Modifier, label: String, detail: String) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "$label:",
            style = AppTheme.typography.paragraph.medium,
            color = AppTheme.colors.foreground.primary
        )
        Text(
            modifier = Modifier.weight(1f, false),
            text = detail.ifBlank { "-" },
            style = AppTheme.typography.paragraph.small,
            color = AppTheme.colors.foreground.secondary
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    AppTheme {
        Surface {
            Layout(
                players = MutableStateFlow(PagingData.from(UIONBAPlayerCard.forPreviewList())).collectAsLazyPagingItems(),
                onNavigate = {}
            )
        }
    }
}