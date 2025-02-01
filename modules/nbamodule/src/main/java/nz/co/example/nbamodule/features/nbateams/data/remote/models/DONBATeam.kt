package nz.co.example.nbamodule.features.nbateams.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nz.co.example.nbamodule.features.nbateams.business.models.NBATeam

@Serializable
internal data class DONBATeam(
    val id: Int,
    val conference: String,
    val division: String,
    val city: String,
    val name: String,
    @SerialName("full_name") val fullName: String,
    val abbreviation: String,
)

internal fun mapFrom(data: DONBATeam): NBATeam {
    return NBATeam(
        id = data.id,
        conference = data.conference,
        division = data.division,
        city = data.city,
        name = data.name,
        fullName = data.fullName,
        abbreviation = data.abbreviation
    )
}
