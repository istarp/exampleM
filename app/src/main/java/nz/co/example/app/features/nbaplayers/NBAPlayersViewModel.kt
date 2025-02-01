package nz.co.example.app.features.nbaplayers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import nz.co.example.app.features.nbaplayers.model.UIONBAPlayerCard
import nz.co.example.app.features.nbaplayers.model.mapFrom
import nz.co.example.nbamodule.features.nbaplayers.NBAPlayersFeature

internal class NBAPlayersViewModel(private val nbaPlayersFeature: NBAPlayersFeature) : ViewModel() {

    val data: StateFlow<PagingData<UIONBAPlayerCard>>
        field = MutableStateFlow(PagingData.empty())

    init {
        collectData()
    }

    private fun collectData() {
        viewModelScope.launch {
            nbaPlayersFeature.getPlayers()
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    data.value = pagingData.map {
                        mapFrom(it)
                    }
                }
        }
    }

}