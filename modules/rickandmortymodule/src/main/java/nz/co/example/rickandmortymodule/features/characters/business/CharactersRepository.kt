package nz.co.example.rickandmortymodule.features.characters.business

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import nz.co.example.coremodule.common.Result
import nz.co.example.rickandmortymodule.features.characters.business.models.Character

internal interface CharactersRepository {
    suspend fun getCharacters(): Flow<PagingData<Character>>
    suspend fun getCharacters(name: String): Flow<PagingData<Character>>
    suspend fun getFavouriteCharacters(): Flow<List<Character>>
    suspend fun getCharacter(id: String): Flow<Result<Character>>
    suspend fun updateCharacter(id: String, isFavourite: Boolean)
}