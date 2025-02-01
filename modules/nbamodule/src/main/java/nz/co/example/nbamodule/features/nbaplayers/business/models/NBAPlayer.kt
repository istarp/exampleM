package nz.co.example.nbamodule.features.nbaplayers.business.models

data class NBAPlayer(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val position: String,
    val teamName: String
)