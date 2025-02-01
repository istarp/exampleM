package nz.co.example.nbamodule.features.nbateams.business

import kotlinx.coroutines.flow.Flow
import nz.co.example.coremodule.common.Result
import nz.co.example.nbamodule.features.nbateams.business.models.NBATeam

internal interface NBATeamsRepository {
    suspend fun getTeam(id: String): Flow<Result<NBATeam>>
}