package nz.co.example.nbamodule.features.nbaplayers.business

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import nz.co.example.coremodule.common.Result
import nz.co.example.nbamodule.features.nbaplayers.business.models.NBAPlayer

internal interface NBAPlayersRepository {
    suspend fun getPlayers(): Flow<PagingData<NBAPlayer>>
    suspend fun getPlayer(id: String): Flow<Result<NBAPlayer>>
}