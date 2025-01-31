package nz.co.example.rickandmortymodule.features.characters.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import nz.co.example.coremodule.common.NetworkResult
import nz.co.example.rickandmortymodule.features.characters.data.models.DOCharacter
import nz.co.example.rickandmortymodule.features.characters.data.models.DOCharactersResponse

internal interface CharactersService {
    suspend fun getCharacters(page: Int, name: String?): NetworkResult<DOCharactersResponse>
    suspend fun getCharacter(id: String): NetworkResult<DOCharacter>

    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/character"
    }
}

internal class CharactersServiceImpl(
    private val client: HttpClient
) : CharactersService {

    override suspend fun getCharacters(page: Int, name: String?): NetworkResult<DOCharactersResponse> {
        return try {
            val response = client.get {
                url(CharactersService.BASE_URL)
                parameter("page", page)
                name?.let { parameter("name", name) }
            }
            if (response.status.value == 404) {
                NetworkResult.NoData
            } else {
                NetworkResult.Success(response.body())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e)
        }
    }

    override suspend fun getCharacter(id: String): NetworkResult<DOCharacter> {
        return try {
            val response = client.get(CharactersService.BASE_URL + "/$id")
            if (response.status.value == 404) {
                NetworkResult.NoData
            } else {
                NetworkResult.Success(response.body())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e)
        }
    }
}