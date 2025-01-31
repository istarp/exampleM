package nz.co.example.app.features.favouritecharacters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nz.co.example.app.ui.components.charactercard.model.UIOCharacterCard
import nz.co.example.app.ui.components.charactercard.model.mapFrom
import nz.co.example.rickandmortymodule.features.characters.CharactersFeature

internal class FavouriteCharactersViewModel(
    private val charactersFeature: CharactersFeature
) : ViewModel() {

    val data: StateFlow<List<UIOCharacterCard>>
        field = MutableStateFlow<List<UIOCharacterCard>>(emptyList())

    init {
        collectData()
    }

    private fun collectData() {
        viewModelScope.launch {
            charactersFeature.getFavouriteCharacters()
                .collect { result ->
                    data.value = result.map { mapFrom(it) }
                }
        }
    }

}