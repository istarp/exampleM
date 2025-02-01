package nz.co.example.nbamodule.features.nbaplayers.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import nz.co.example.nbamodule.features.nbaplayers.business.models.NBAPlayer
import nz.co.example.nbamodule.features.nbaplayers.business.models.NBAPlayerTeam
import nz.co.example.nbamodule.features.nbaplayers.data.remote.models.DONBAPlayer

@Entity(tableName = "nba_players")
internal data class EntityNBAPlayer(
    @PrimaryKey val id: Int,
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
    val team: Team
) {
    @Serializable
    internal data class Team(
        val id: Int,
        val name: String
    )
}

@ProvidedTypeConverter
internal class TeamConverter {
    @TypeConverter
    fun stringToTeam(string: String): EntityNBAPlayer.Team {
        return Json.decodeFromString(string)
    }

    @TypeConverter
    fun teamToString(team: EntityNBAPlayer.Team?): String {
        return Json.encodeToString(team)
    }
}

internal fun mapFrom(data: DONBAPlayer): EntityNBAPlayer {
    return EntityNBAPlayer(
        id = data.id,
        firstName = data.firstName,
        lastName = data.lastName,
        position = data.position,
        height = data.height ?: "",
        weight = data.weight ?: "",
        jerseyNumber = data.jerseyNumber ?: "",
        college = data.college ?: "",
        country = data.country ?: "",
        draftYear = (data.draftYear ?: "-").toString(),
        draftRound = (data.draftRound ?: "-").toString(),
        draftNumber = (data.draftNumber ?: "-").toString(),
        team = EntityNBAPlayer.Team(id = data.team.id, name = data.team.name)
    )
}

internal fun mapFrom(data: EntityNBAPlayer): NBAPlayer {
    return NBAPlayer(
        id = data.id,
        firstName = data.firstName,
        lastName = data.lastName,
        position = data.position,
        height = data.height,
        weight = data.weight,
        jerseyNumber = data.jerseyNumber,
        college = data.college,
        country = data.country,
        draftYear = data.draftYear,
        draftRound = data.draftRound,
        draftNumber = data.draftNumber,
        team = NBAPlayerTeam(id = data.team.id, name = data.team.name)
    )
}