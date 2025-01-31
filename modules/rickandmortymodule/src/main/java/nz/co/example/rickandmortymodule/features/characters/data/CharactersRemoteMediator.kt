package nz.co.example.rickandmortymodule.features.characters.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState.Loading.endOfPaginationReached
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import nz.co.example.coremodule.common.NetworkResult
import nz.co.example.rickandmortymodule.features.characters.data.models.DOCharacter
import nz.co.example.rickandmortymodule.features.characters.data.models.DOCharacterRemoteKeys
import nz.co.example.rickandmortymodule.features.database.Database
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
internal class CharactersRemoteMediator(
    private val database: Database,
    private val service: CharactersService
) : RemoteMediator<Int, DOCharacter>() {

    override suspend fun initialize(): InitializeAction {
        val remoteKey = database.withTransaction {
            database.charactersRemoteKeysDao.getFirstRemoteKey()
        } ?: return InitializeAction.LAUNCH_INITIAL_REFRESH

        val cacheTimeout = TimeUnit.MILLISECONDS.convert(10, TimeUnit.MINUTES)
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
                    remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
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

            when (val response = service.getCharacters(page = page, name = null)) {
                is NetworkResult.Error -> MediatorResult.Success(endOfPaginationReached = false)
                NetworkResult.NoData -> MediatorResult.Success(endOfPaginationReached = true)
                is NetworkResult.Success -> {
                    val prevKey = if (page == STARTING_PAGE_INDEX) null else page.minus(1)
                    val nextKey = if ((endOfPaginationReached)) null else page.plus(1)

                    database.withTransaction {
                        database.charactersDao.insertAll(response.data.results)

                        val remoteKeys = response.data.results.map {
                            DOCharacterRemoteKeys(
                                characterId = it.id,
                                prevKey = prevKey,
                                nextKey = nextKey,
                                lastUpdated = System.currentTimeMillis()
                            )
                        }
                        database.charactersRemoteKeysDao.insertAll(remoteKeys)
                    }

                    MediatorResult.Success(endOfPaginationReached = response.data.info.next == null)
                }
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    companion object {
        const val STARTING_PAGE_INDEX = 1
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

