package nz.co.example.rickandmortymodule.features.characters.data.local

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState.Loading.endOfPaginationReached
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import nz.co.example.coremodule.common.NetworkResult
import nz.co.example.rickandmortymodule.features.api.utils.callApi
import nz.co.example.rickandmortymodule.features.characters.data.models.DOCharacter
import nz.co.example.rickandmortymodule.features.characters.data.models.DOCharacterRemoteKeys
import nz.co.example.rickandmortymodule.features.characters.data.remote.CharactersApiService
import nz.co.example.rickandmortymodule.features.database.Database
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
internal class CharactersRemoteMediator(
    private val database: Database,
    private val service: CharactersApiService
) : RemoteMediator<Int, DOCharacter>() {

    private val recordsPerPage = 35

    override suspend fun initialize(): InitializeAction {
        val remoteKey = database.withTransaction {
            database.charactersRemoteKeysDao.getFirstRemoteKey()
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
        state: PagingState<Int, DOCharacter>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(recordsPerPage) ?: STARTING_PAGE_CURSOR
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    remoteKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
            }

            when (val response = callApi { service.getCharacters(perPage = recordsPerPage, cursor = page) }) {
                is NetworkResult.Error -> MediatorResult.Error(response.exception)
                NetworkResult.NoData -> MediatorResult.Success(endOfPaginationReached = true)
                is NetworkResult.Success -> {
                    val prevKey = if (page == STARTING_PAGE_CURSOR) null else page.minus(recordsPerPage)
                    val nextKey = if ((endOfPaginationReached)) null else page.plus(recordsPerPage)

                    database.withTransaction {
                        database.charactersDao.insertAll(response.data.data)

                        val remoteKeys = response.data.data.map {
                            DOCharacterRemoteKeys(
                                characterId = it.id,
                                prevKey = prevKey,
                                nextKey = nextKey,
                                lastUpdated = System.currentTimeMillis()
                            )
                        }
                        database.charactersRemoteKeysDao.insertAll(remoteKeys)
                    }

                    MediatorResult.Success(endOfPaginationReached = response.data.meta.nextCursor == null)
                }
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    companion object {
        const val STARTING_PAGE_CURSOR = 0
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, DOCharacter>): DOCharacterRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { database.charactersRemoteKeysDao.remoteKeysRepoId(it.id) }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, DOCharacter>): DOCharacterRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { database.charactersRemoteKeysDao.remoteKeysRepoId(it.id) }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, DOCharacter>): DOCharacterRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.charactersRemoteKeysDao.remoteKeysRepoId(id)
            }
        }
    }

}

