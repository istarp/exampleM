package nz.co.example.app.features.characterdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nz.co.example.app.features.characterdetail.model.UIOCharacterDetail
import nz.co.example.app.features.characterdetail.model.mapFrom
import nz.co.example.app.ui.lce.LCEState
import nz.co.example.coremodule.common.Result
import nz.co.example.nbamodule.features.nbaplayers.NBAPlayersFeature

internal class CharacterDetailViewModel(
    private val id: String, private val nbaPlayersFeature: NBAPlayersFeature
) : ViewModel() {

    val data: StateFlow<LCEState<UIOCharacterDetail>>
        field = MutableStateFlow<LCEState<UIOCharacterDetail>>(LCEState.Loading())

    init {
        collectData()
    }

    fun onToggleFavourite() {
        val current = (data.value as? LCEState.Content)?.value ?: return
        viewModelScope.launch {
         //   charactersFeature.setFavouriteCharacter(current.id.toString(), current.isFavourite.not())
        }
    }

    private fun collectData() {
        viewModelScope.launch {
            nbaPlayersFeature.getPlayer(id)
                .collect { result ->
                    data.value = when (result) {
                        is Result.Data -> LCEState.Content(mapFrom(result.data))
                        is Result.Error -> LCEState.Error(result.exception.message ?: "")
                        Result.Loading -> LCEState.Loading()
                    }
                }
        }
    }

}