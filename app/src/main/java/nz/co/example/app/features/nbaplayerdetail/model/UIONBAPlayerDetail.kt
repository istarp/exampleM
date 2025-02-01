package nz.co.example.app.features.nbaplayerdetail.model

import nz.co.example.nbamodule.features.nbaplayers.business.models.NBAPlayer

internal data class UIONBAPlayerDetail(
    val name: String,
    val position: String,
    val height: String,
    val weight: String,
    val jerseyNumber: String,
    val college: String,
    val country: String,
    val draftYear: String,
    val draftRound: String,
    val draftNumber: String,
    val teamId: String,
    val teamName: String
) {
    companion object {
        fun forPreview(): UIONBAPlayerDetail {
            return UIONBAPlayerDetail(
                name = "Rick Sanchez",
                position = "G",
                height = "12",
                weight = "56",
                jerseyNumber = "10",
                college = "Northwestern",
                country = "USA",
                draftYear = "2000",
                draftRound = "1",
                draftNumber = "1",
                teamId = "1",
                teamName = "Cavaliers"
            )
        }
    }
}

internal fun mapFrom(data: NBAPlayer): UIONBAPlayerDetail {
    return UIONBAPlayerDetail(
        name = data.firstName + " " + data.lastName,
        position = data.position,
        height = data.height,
        weight = data.weight,
        jerseyNumber = data.jerseyNumber,
        college = data.college,
        country = data.country,
        draftYear = data.draftYear,
        draftRound = data.draftRound,
        draftNumber = data.draftNumber,
        teamId = data.team.id.toString(),
        teamName = data.team.name
    )
}