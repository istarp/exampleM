package nz.co.example.nbamodule.features.nbaplayers.business

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import nz.co.example.coremodule.common.Result
import nz.co.example.nbamodule.features.nbaplayers.NBAPlayersFacade
import nz.co.example.nbamodule.features.nbaplayers.business.models.NBAPlayer

internal class NBAPlayersUseCase(private val repository: NBAPlayersRepository) : NBAPlayersFacade {

    override suspend fun getPlayers(): Flow<PagingData<NBAPlayer>> {
        return repository.getPlayers()
    }

    override suspend fun getPlayer(id: String): Flow<Result<NBAPlayer>> {
        return repository.getPlayer(id)
    }

}