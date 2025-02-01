package nz.co.example.nbamodule.features.nbaplayers.data.remote

import nz.co.example.nbamodule.features.nbaplayers.data.remote.models.DONBAPlayerResponse
import nz.co.example.nbamodule.features.nbaplayers.data.remote.models.DONBAPlayersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface NBAPlayersApiService {
    @GET("players")
    suspend fun getPlayers(
        @Query("per_page") perPage: Int,
        @Query("cursor") cursor: Int?
    ): Response<DONBAPlayersResponse>

    @GET("players/{id}")
    suspend fun getPlayer(@Path("id") id: String): Response<DONBAPlayerResponse>
}