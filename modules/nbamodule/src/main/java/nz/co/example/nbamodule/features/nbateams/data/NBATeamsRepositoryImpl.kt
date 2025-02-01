package nz.co.example.nbamodule.features.nbateams.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import nz.co.example.coremodule.common.NetworkResult
import nz.co.example.coremodule.common.Result
import nz.co.example.coremodule.features.coroutinedispatchers.CoroutineDispatchersFeature
import nz.co.example.nbamodule.features.api.utils.callApi
import nz.co.example.nbamodule.features.nbateams.business.NBATeamsRepository
import nz.co.example.nbamodule.features.nbateams.business.models.NBATeam
import nz.co.example.nbamodule.features.nbateams.data.remote.NBATeamsApiService
import nz.co.example.nbamodule.features.nbateams.data.remote.models.mapFrom

internal class NBATeamsRepositoryImpl(
    private val service: NBATeamsApiService,
    private val dispatchers: CoroutineDispatchersFeature
) : NBATeamsRepository {

    override suspend fun getTeam(id: String): Flow<Result<NBATeam>> {
        return withContext(dispatchers.io()) {
            flow {
                emit(Result.Loading)
                val result = when (val result = callApi { service.getTeam(id) }) {
                    is NetworkResult.Error -> Result.Error(result.exception)
                    NetworkResult.NoData -> Result.Error(Exception("No data"))
                    is NetworkResult.Success -> Result.Data(mapFrom(result.value.data))
                }
                emit(result)
            }
        }
    }

}