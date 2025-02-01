package nz.co.example.nbamodule.features.nbateams.data.remote

import nz.co.example.nbamodule.features.nbateams.data.remote.models.DONBATeamResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

internal interface NBATeamsApiService {
    @GET("teams/{id}")
    suspend fun getTeam(@Path("id") id: String): Response<DONBATeamResponse>
}