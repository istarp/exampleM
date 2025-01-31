package nz.co.example.rickandmortymodule.features.characters.business

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import nz.co.example.coremodule.common.Result
import nz.co.example.rickandmortymodule.features.characters.CharactersFeature
import nz.co.example.rickandmortymodule.features.characters.business.models.Character

internal class CharactersUseCase(
    private val repository: CharactersRepository,
) : CharactersFeature {

    override suspend fun getCharacters(): Flow<PagingData<Character>> {
        return repository.getCharacters()
    }

    override suspend fun getCharacter(id: String): Flow<Result<Character>> {
        return repository.getCharacter(id)
    }

}