package nz.co.example.app.features.nbaplayerdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nz.co.example.app.features.nbaplayerdetail.model.UIONBAPlayerDetail
import nz.co.example.app.features.nbaplayerdetail.model.mapFrom
import nz.co.example.app.ui.lce.LCEState
import nz.co.example.coremodule.common.Result
import nz.co.example.nbamodule.features.nbaplayers.NBAPlayersFacade

internal class NBAPlayerDetailViewModel(
    private val id: String, private val nbaPlayers: NBAPlayersFacade
) : ViewModel() {

    val data: StateFlow<LCEState<UIONBAPlayerDetail>>
        field = MutableStateFlow<LCEState<UIONBAPlayerDetail>>(LCEState.Loading())

    init {
        collectData()
    }

    private fun collectData() {
        viewModelScope.launch {
            nbaPlayers.getPlayer(id)
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