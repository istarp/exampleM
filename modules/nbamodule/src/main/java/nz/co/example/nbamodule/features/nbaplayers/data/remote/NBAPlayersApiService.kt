package nz.co.example.nbamodule.features.nbaplayers.data.remote

import nz.co.example.coremodule.common.NetworkResult
import nz.co.example.nbamodule.features.nbaplayers.data.remote.models.DONBAPlayer
import nz.co.example.nbamodule.features.nbaplayers.data.remote.models.DONBAPlayers
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface NBAPlayersApiService {
    @GET("players")
    suspend fun getPlayers(@Query("per_page") perPage: Int, @Query("cursor") cursor: Int?): Response<DONBAPlayers>

    @GET("players")
    suspend fun getPlayer(@Path("id") id: String): NetworkResult<DONBAPlayer>
}