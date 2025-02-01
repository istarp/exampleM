package nz.co.example.nbamodule.features.nbaplayers.data.local

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState.Loading.endOfPaginationReached
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import nz.co.example.coremodule.common.NetworkResult
import nz.co.example.nbamodule.features.api.utils.callApi
import nz.co.example.nbamodule.features.database.Database
import nz.co.example.nbamodule.features.nbaplayers.data.local.models.EntityNBAPlayer
import nz.co.example.nbamodule.features.nbaplayers.data.local.models.EntityNBAPlayerRemoteKeys
import nz.co.example.nbamodule.features.nbaplayers.data.local.models.mapFrom
import nz.co.example.nbamodule.features.nbaplayers.data.remote.NBAPlayersApiService
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
internal class NBAPlayersRemoteMediator(
    private val database: Database,
    private val service: NBAPlayersApiService
) : RemoteMediator<Int, EntityNBAPlayer>() {

    private val recordsPerPage = 35

    override suspend fun initialize(): InitializeAction {
        val remoteKey = database.withTransaction {
            database.nbaPlayersRemoteKeysDao.getFirstRemoteKey()
        } ?: return InitializeAction.LAUNCH_INITIAL_REFRESH

        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS) //todo change back to 10 minutes
        return if (System.currentTimeMillis() - remoteKey.lastUpdated <= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EntityNBAPlayer>
    ): MediatorResult {
        return try {
            val cursor = when (loadType) {
                LoadType.REFRESH -> null

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    remoteKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
            }

            when (val response = callApi { service.getPlayers(perPage = recordsPerPage, cursor = cursor) }) {
                is NetworkResult.Error -> MediatorResult.Error(response.exception)
                NetworkResult.NoData -> MediatorResult.Success(endOfPaginationReached = true)
                is NetworkResult.Success -> {
                    database.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            database.nbaPlayersRemoteKeysDao.clearAll()
                            database.nbaPlayersDao.clearAll()
                        }

                        database.nbaPlayersDao.insertAll(response.value.data.map { mapFrom(it) })

                        val data = response.value
                        val prevKey = data.meta.prevCursor
                        val nextKey = if ((endOfPaginationReached)) null else data.meta.nextCursor

                        val remoteKeys = response.value.data.map {
                            mapFrom(playerId = it.id, prevKey = prevKey, nextKey = nextKey)
                        }
                        database.nbaPlayersRemoteKeysDao.insertAll(remoteKeys)
                    }

                    MediatorResult.Success(endOfPaginationReached = response.value.meta.nextCursor == null)
                }
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, EntityNBAPlayer>): EntityNBAPlayerRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { database.nbaPlayersRemoteKeysDao.remoteKeysRepoId(it.id) }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, EntityNBAPlayer>): EntityNBAPlayerRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { database.nbaPlayersRemoteKeysDao.remoteKeysRepoId(it.id) }
    }

}