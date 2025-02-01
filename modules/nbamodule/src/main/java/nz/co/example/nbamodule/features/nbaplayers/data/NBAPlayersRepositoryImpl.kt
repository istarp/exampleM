@file:OptIn(ExperimentalPagingApi::class)

package nz.co.example.nbamodule.features.nbaplayers.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import nz.co.example.coremodule.common.NetworkResult
import nz.co.example.coremodule.common.Result
import nz.co.example.coremodule.features.coroutinedispatchers.CoroutineDispatchersFeature
import nz.co.example.nbamodule.features.api.utils.callApi
import nz.co.example.nbamodule.features.database.Database
import nz.co.example.nbamodule.features.nbaplayers.business.NBAPlayersRepository
import nz.co.example.nbamodule.features.nbaplayers.business.models.NBAPlayer
import nz.co.example.nbamodule.features.nbaplayers.data.local.NBAPlayersRemoteMediator
import nz.co.example.nbamodule.features.nbaplayers.data.local.models.mapFrom
import nz.co.example.nbamodule.features.nbaplayers.data.remote.NBAPlayersApiService

internal class NBAPlayersRepositoryImpl(
    private val service: NBAPlayersApiService,
    private val database: Database,
    private val dispatchers: CoroutineDispatchersFeature
) : NBAPlayersRepository {

    override suspend fun getPlayers(): Flow<PagingData<NBAPlayer>> {
        return Pager(config = PagingConfig(pageSize = 35),
            initialKey = 1,
            remoteMediator = NBAPlayersRemoteMediator(service = service, database = database),
            pagingSourceFactory = { database.nbaPlayersDao.getPlayers() }).flow.map { pagingData ->
            pagingData.map { page ->
                mapFrom(page)
            }
        }
    }

    override suspend fun getPlayer(id: String): Flow<Result<NBAPlayer>> {
        return withContext(dispatchers.io()) {
            if (database.nbaPlayersDao.retrievePlayer(id) == null) {
                when (val result = callApi { service.getPlayer(id) }) {
                    is NetworkResult.Success -> Result.Data(mapFrom(result.value.data)).also {
                        database.nbaPlayersDao.insertAll(listOf(mapFrom(result.value.data)))
                    }

                    else -> {}
                }
            }

            database.nbaPlayersDao.getPlayer(id).map {
                if (it != null) {
                    Result.Data(mapFrom(it))
                } else {
                    Result.Error(Exception("No data"))
                }
            }
        }
    }

}