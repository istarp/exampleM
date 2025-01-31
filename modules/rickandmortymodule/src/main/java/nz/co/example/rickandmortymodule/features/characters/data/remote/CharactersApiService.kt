package nz.co.example.rickandmortymodule.features.characters.data.remote

import nz.co.example.coremodule.common.NetworkResult
import nz.co.example.rickandmortymodule.features.characters.data.models.DOCharacter
import nz.co.example.rickandmortymodule.features.characters.data.models.DOCharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface CharactersApiService {
    @GET("players")
    suspend fun getCharacters(@Query("per_page") perPage: Int, @Query("cursor") cursor: Int): Response<DOCharactersResponse>

    @GET("character")
    suspend fun getCharacter(@Path("id") id: String): NetworkResult<DOCharacter>
}