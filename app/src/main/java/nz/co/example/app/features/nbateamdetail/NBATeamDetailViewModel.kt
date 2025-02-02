package nz.co.example.app.features.nbateamdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nz.co.example.app.features.nbateamdetail.models.UIONBATeamDetail
import nz.co.example.app.features.nbateamdetail.models.mapFrom
import nz.co.example.app.ui.lce.LCEState
import nz.co.example.coremodule.common.Result
import nz.co.example.nbamodule.features.nbateams.NBATeamsFacade

internal class NBATeamDetailViewModel(private val id: String, private val nbaTeams: NBATeamsFacade) : ViewModel() {

    val data: StateFlow<LCEState<UIONBATeamDetail>>
        field = MutableStateFlow<LCEState<UIONBATeamDetail>>(LCEState.Loading())

    init {
        collectData()
    }

    private fun collectData() {
        viewModelScope.launch {
            nbaTeams.getTeam(id)
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