package nz.co.example.nbamodule.features.nbateams.business.models

data class NBATeam(
    val id: Int,
    val conference: String,
    val division: String,
    val city: String,
    val name: String,
    val fullName: String,
    val abbreviation: String
)