@file:OptIn(ExperimentalPagingApi::class)

package nz.co.example.rickandmortymodule.features.characters.data

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
import nz.co.example.rickandmortymodule.features.characters.business.CharactersRepository
import nz.co.example.rickandmortymodule.features.characters.business.models.Character
import nz.co.example.rickandmortymodule.features.characters.data.models.mapFrom
import nz.co.example.rickandmortymodule.features.database.Database

internal class CharactersRepositoryImpl(
    private val service: CharactersService,
    private val database: Database,
    private val dispatchers: CoroutineDispatchersFeature
) : CharactersRepository {

    override suspend fun getCharacters(): Flow<PagingData<Character>> {
        return Pager(config = PagingConfig(pageSize = 100),
            initialKey = 1,
            remoteMediator = CharactersRemoteMediator(service = service, database = database),
            pagingSourceFactory = { database.charactersDao.getCharacters() }).flow.map { pagingData ->
            pagingData.map { page ->
                mapFrom(page)
            }
        }
    }

    override suspend fun getCharacters(name: String): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(pageSize = 100),
            initialKey = 1,
            pagingSourceFactory = { CharactersPagingSource(name = name, service = service) },
        ).flow.map { pagingData ->
            pagingData.map { page ->
                mapFrom(page)
            }
        }
    }

    override suspend fun getFavouriteCharacters(): Flow<List<Character>> {
        return withContext(dispatchers.io()) {
            database.charactersDao.getFavouriteCharacters().map { items ->
                items.map { mapFrom(it) }
            }
        }
    }

    override suspend fun getCharacter(id: String): Flow<Result<Character>> {
        return withContext(dispatchers.io()) {
            if (database.charactersDao.retrieveCharacter(id) == null) {
                when (val result = service.getCharacter(id)) {
                    is NetworkResult.Success -> Result.Data(mapFrom(result.data)).also {
                        database.charactersDao.insertAll(listOf(result.data))
                    }

                    else -> {}
                }
            }

            database.charactersDao.getCharacter(id).map {
                if (it != null) {
                    Result.Data(mapFrom(it))
                } else {
                    Result.Error(Exception("No data"))
                }
            }
        }
    }

    override suspend fun updateCharacter(id: String, isFavourite: Boolean) {
        withContext(dispatchers.io()) {
            database.charactersDao.retrieveCharacter(id)?.let {
                database.charactersDao.updateCharacter(it.copy(isFavourite = isFavourite))
            }
        }
    }
}