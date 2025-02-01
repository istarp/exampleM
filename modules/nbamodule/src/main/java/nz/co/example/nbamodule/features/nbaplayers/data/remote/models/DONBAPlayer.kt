package nz.co.example.nbamodule.features.nbaplayers.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class DONBAPlayer(
    val id: Int,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    val position: String,
    val height: String?,
    val weight: String?,
    @SerialName("jersey_number") val jerseyNumber: String?,
    val college: String?,
    val country: String?,
    @SerialName("draft_year") val draftYear: Int?,
    @SerialName("draft_round") val draftRound: Int?,
    @SerialName("draft_number") val draftNumber: Int?,
    val team: DOTeam,
) {
    @Serializable
    internal data class DOTeam(
        val id: Int,
        val name: String,
    )
}