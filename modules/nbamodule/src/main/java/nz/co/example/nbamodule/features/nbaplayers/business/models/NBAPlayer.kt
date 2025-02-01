package nz.co.example.nbamodule.features.nbaplayers.business.models

data class NBAPlayer(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val position: String,
    val height: String,
    val weight: String,
    val jerseyNumber: String,
    val college: String,
    val country: String,
    val draftYear: String,
    val draftRound: String,
    val draftNumber: String,
    val team: NBAPlayerTeam
)