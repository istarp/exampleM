package nz.co.example.app.features.nbateamdetail.models

import nz.co.example.nbamodule.features.nbateams.business.models.NBATeam

internal data class UIONBATeamDetail(
    val name: String,
    val conference: String,
    val division: String,
    val city: String,
    val fullName: String,
    val abbreviation: String
) {

    companion object {
        fun forPreview(): UIONBATeamDetail {
            return UIONBATeamDetail(
                name = "Celtics",
                conference = "East",
                division = "Atlantic",
                city = "Boston",
                fullName = "Boston Celtics",
                abbreviation = "BOS"
            )
        }
    }
}

internal fun mapFrom(data: NBATeam): UIONBATeamDetail {
    return UIONBATeamDetail(
        name = data.name,
        conference = data.conference,
        division = data.division,
        city = data.city,
        fullName = data.fullName,
        abbreviation = data.abbreviation
    )
}