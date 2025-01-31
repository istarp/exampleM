@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package nz.co.example.app.features.characters

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import kotlinx.coroutines.flow.MutableStateFlow
import nz.co.example.app.R
import nz.co.example.app.features.characters.models.UIOCharacterScreenState
import nz.co.example.app.features.navigation.fadeIn
import nz.co.example.app.features.navigation.fadeOut
import nz.co.example.app.features.navigation.models.AppNavigationRoute
import nz.co.example.app.features.navigation.models.GenericNavigation
import nz.co.example.app.features.navigation.models.RouteNavigation
import nz.co.example.app.ui.components.BackButton
import nz.co.example.app.ui.components.charactercard.CharacterCard
import nz.co.example.app.ui.components.charactercard.model.UIOCharacterCard
import nz.co.example.app.ui.components.image.Image
import nz.co.example.app.ui.components.topbar.TopAppBar
import nz.co.example.app.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun CharactersScreen(
    modifier: Modifier = Modifier,
    onNavigate: (GenericNavigation) -> Unit,
    viewModel: CharactersViewModel = koinViewModel()
) {
    val characters = viewModel.data.collectAsLazyPagingItems()
    val filteredCharacters = viewModel.searchData.collectAsLazyPagingItems()
    val state by viewModel.state.collectAsStateWithLifecycle()

    Box(modifier = modifier) {
        Layout(
            state = state,
            characters = characters,
            filteredCharacters = filteredCharacters,
            onNavigate = onNavigate,
            onSearchTextChange = viewModel::onSearchTextChange,
            onToggleSearch = viewModel::onToggleSearch
        )
    }
}

@Composable
private fun Layout(
    state: UIOCharacterScreenState,
    characters: LazyPagingItems<UIOCharacterCard>,
    filteredCharacters: LazyPagingItems<UIOCharacterCard>,
    onNavigate: (GenericNavigation) -> Unit,
    onSearchTextChange: (String) -> Unit,
    onToggleSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler(state.isSearching) {
        onToggleSearch()
    }
    Column(modifier = modifier) {
        TopAppBar(
            title = {
                Box(contentAlignment = Alignment.CenterStart) {
                    Text(
                        text = stringResource(R.string.characters_title),
                        maxLines = 1,
                        style = AppTheme.typography.headline.headline2,
                        color = AppTheme.colors.foreground.primary,
                        overflow = TextOverflow.Ellipsis
                    )
                    androidx.compose.animation.AnimatedVisibility(
                        enter = fadeIn(),
                        exit = fadeOut(),
                        visible = state.isSearching
                    ) {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(AppTheme.colors.background.primary),
                            value = state.searchText,
                            onValueChange = { text ->
                                onSearchTextChange(text)
                            },
                            placeholder = {
                                Text(
                                    text = stringResource(R.string.search_characters_placeholder),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = AppTheme.typography.paragraph.medium,
                                    color = AppTheme.colors.foreground.secondary
                                )
                            },
                            textStyle = AppTheme.typography.paragraph.medium,
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                focusedTextColor = AppTheme.colors.foreground.primary,
                                unfocusedTextColor = AppTheme.colors.foreground.primary
                            )
                        )
                    }
                }
            },
            navigationIcon = {
                AnimatedVisibility(
                    visible = state.isSearching,
                    enter = expandHorizontally(),
                    exit = shrinkHorizontally()
                ) {
                    BackButton(onClick = { onToggleSearch() })
                }
            },
            actions = {
                AnimatedVisibility(visible = !state.isSearching) {
                    IconButton(modifier = modifier, onClick = { onToggleSearch() }) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(R.drawable.ic_search),
                            contentDescription = stringResource(R.string.content_desc_search_characters),
                            tint = AppTheme.colors.foreground.primary,
                        )
                    }
                }
                AnimatedVisibility(visible = state.isSearching && state.searchText.isNotBlank()) {
                    IconButton(modifier = modifier, onClick = { onSearchTextChange("") }) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(R.drawable.ic_close),
                            contentDescription = stringResource(R.string.content_desc_search_characters)
                        )
                    }
                }
            }
        )

        Box {
            Crossfade(
                modifier = Modifier
                    .fillMaxSize(),
                targetState = state.isSearching,
                label = "characters search"
            ) { isSearching ->
                when (isSearching) {
                    false -> CharactersLCE(characters = characters, onNavigate = onNavigate)
                    true -> FilteredCharacters(characters = filteredCharacters, onNavigate = onNavigate)
                }
            }
        }
    }
}

@Composable
private fun CharactersLCE(
    characters: LazyPagingItems<UIOCharacterCard>,
    onNavigate: (GenericNavigation) -> Unit,
    modifier: Modifier = Modifier
) = when {
    characters.loadState.refresh is LoadState.Loading && characters.itemCount == 0 -> {
        Loading(modifier = Modifier.fillMaxSize())
    }

    characters.loadState.refresh is LoadState.Error -> {
        Error(modifier = Modifier.fillMaxSize())
    }

    else -> {
        Characters(modifier, characters, onNavigate)
    }
}

@Composable
private fun Characters(
    modifier: Modifier,
    characters: LazyPagingItems<UIOCharacterCard>,
    onNavigate: (GenericNavigation) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(count = characters.itemCount, key = characters.itemKey { it.id }) { index ->
            characters[index]?.let {
                CharacterCard(
                    modifier = Modifier.fillMaxWidth(),
                    model = it,
                    onClick = { card ->
                        onNavigate(RouteNavigation(AppNavigationRoute.CharacterDetail.createRoute(card.id.toString())))
                    }
                )
            }
        }
    }
}

@Composable
private fun FilteredCharacters(
    modifier: Modifier = Modifier,
    characters: LazyPagingItems<UIOCharacterCard>,
    onNavigate: (GenericNavigation) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(count = characters.itemCount, key = characters.itemKey { it.id }) { index ->
            characters[index]?.let {
                FilteredCharacter(
                    modifier = Modifier.fillMaxWidth(),
                    id = it.id.toString(),
                    name = it.name,
                    imageUrl = it.imageUrl,
                    status = it.status,
                    onNavigate = onNavigate
                )
            }
        }
    }
}

@Composable
private fun FilteredCharacter(
    modifier: Modifier = Modifier,
    id: String,
    name: String,
    imageUrl: String,
    status: String,
    onNavigate: (GenericNavigation) -> Unit,
) {
    Row(
        modifier = modifier
            .clickable { onNavigate(RouteNavigation(AppNavigationRoute.CharacterDetail.createRoute(id))) }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(8.dp)),
            imageUrl = imageUrl
        )
        Box(modifier = Modifier.width(8.dp))
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.weight(1f, false),
                    text = name,
                    style = AppTheme.typography.headline.headline3,
                    color = AppTheme.colors.foreground.primary
                )
            }
            Text(
                text = status,
                style = AppTheme.typography.paragraph.small,
                color = AppTheme.colors.foreground.secondary
            )
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

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewData() {
    AppTheme {
        Surface {
            Layout(
                state = UIOCharacterScreenState.default(),
                characters = MutableStateFlow(PagingData.from(UIOCharacterCard.forPreviewList())).collectAsLazyPagingItems(),
                filteredCharacters = MutableStateFlow(PagingData.from(UIOCharacterCard.forPreviewList())).collectAsLazyPagingItems(),
                onNavigate = {},
                onSearchTextChange = {},
                onToggleSearch = {}
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewSearch() {
    AppTheme {
        Surface {
            Layout(
                state = UIOCharacterScreenState.previewSearchWithoutText(),
                characters = MutableStateFlow(PagingData.from(UIOCharacterCard.forPreviewList())).collectAsLazyPagingItems(),
                filteredCharacters = MutableStateFlow(PagingData.from(UIOCharacterCard.forPreviewList())).collectAsLazyPagingItems(),
                onNavigate = {},
                onSearchTextChange = {},
                onToggleSearch = {}
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewSearchWithText() {
    AppTheme {
        Surface {
            Layout(
                state = UIOCharacterScreenState.previewSearchWithText(),
                characters = MutableStateFlow(PagingData.from(UIOCharacterCard.forPreviewList())).collectAsLazyPagingItems(),
                filteredCharacters = MutableStateFlow(PagingData.from(UIOCharacterCard.forPreviewList())).collectAsLazyPagingItems(),
                onNavigate = {},
                onSearchTextChange = {},
                onToggleSearch = {}
            )
        }
    }
}