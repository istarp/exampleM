package nz.co.example.nbamodule.features.nbaplayers.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import nz.co.example.nbamodule.features.nbaplayers.business.models.NBAPlayer
import nz.co.example.nbamodule.features.nbaplayers.data.remote.models.DONBAPlayer

@Entity(tableName = "nba_players")
internal data class EntityNBAPlayer(
    @PrimaryKey val id: Int,
    val firstName: String,
    val lastName: String,
    val position: String,
    val teamName: String
)

internal fun mapFrom(data: DONBAPlayer): EntityNBAPlayer {
    return EntityNBAPlayer(
        id = data.id,
        firstName = data.firstName,
        lastName = data.lastName,
        position = data.position,
        teamName = data.team.name
    )
}

internal fun mapFrom(data: EntityNBAPlayer): NBAPlayer {
    return NBAPlayer(
        id = data.id,
        firstName = data.firstName,
        lastName = data.lastName,
        position = data.position,
        teamName = data.teamName
    )
}