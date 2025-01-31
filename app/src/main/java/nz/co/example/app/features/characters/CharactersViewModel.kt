package nz.co.example.app.features.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import nz.co.example.app.ui.components.charactercard.model.UIOCharacterCard
import nz.co.example.app.features.characters.models.UIOCharacterScreenState
import nz.co.example.app.ui.components.charactercard.model.mapFrom
import nz.co.example.rickandmortymodule.features.characters.CharactersFeature

internal class CharactersViewModel(private val charactersFeature: CharactersFeature) : ViewModel() {

    val data: StateFlow<PagingData<UIOCharacterCard>>
        field = MutableStateFlow(PagingData.empty())

    val searchData: StateFlow<PagingData<UIOCharacterCard>>
        field = MutableStateFlow(PagingData.empty())

    val state: StateFlow<UIOCharacterScreenState>
        field = MutableStateFlow(UIOCharacterScreenState.default())

    private var searchCollectionJob: Job? = null

    init {
        collectData()
    }

    private fun collectData() {
        viewModelScope.launch {
            charactersFeature.getCharacters()
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    data.value = pagingData.map {
                        mapFrom(it)
                    }
                }
        }
    }

    fun onToggleSearch() {
        val isSearching = !state.value.isSearching
        if (!isSearching) {
            searchCollectionJob = null
            searchData.value = PagingData.empty()
        }
        state.value = state.value.copy(
            isSearching = isSearching,
            searchText = if (!isSearching) "" else state.value.searchText
        )
    }

    fun onSearchTextChange(text: String) {
        searchCollectionJob?.cancel()
        state.value = state.value.copy(searchText = text)
        if (text.isBlank()) {
            searchData.value = PagingData.empty()
        } else {
        }
    }

}