package nz.co.example.rickandmortymodule.features.characters.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import nz.co.example.rickandmortymodule.features.characters.business.models.Character

//TODO ideally should be another entity class so it is not mixed with Api Data Object
@Serializable
@Entity(tableName = "characters")
internal data class DOCharacter(
    @PrimaryKey val id: Int,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    val team: DOTeam,
) {
    @Serializable
    internal data class DOTeam(
        val id: Int,
    )
}

internal fun mapFrom(data: DOCharacter): Character {
    return Character(
        id = data.id,
        name = data.firstName + " : " + data.lastName,
        imageUrl = "",
        status = "",
        species = "",
        type = "",
        gender = "",
        origin = "",
        location = "",
        isFavourite = false
    )
}

@ProvidedTypeConverter
internal class TeamConverter {
    @TypeConverter
    fun stringToTeam(string: String): DOCharacter.DOTeam {
        return Json.decodeFromString(string)
    }

    @TypeConverter
    fun teamToString(origin: DOCharacter.DOTeam?): String {
       return Json.encodeToString(origin)
    }
}
