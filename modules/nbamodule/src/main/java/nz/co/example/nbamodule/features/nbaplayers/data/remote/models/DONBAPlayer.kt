package nz.co.example.nbamodule.features.nbaplayers.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class DONBAPlayer(
    val id: Int,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    val position: String,
    val team: DOTeam,
) {
    @Serializable
    internal data class DOTeam(
        val id: Int,
        val name: String,
    )
}