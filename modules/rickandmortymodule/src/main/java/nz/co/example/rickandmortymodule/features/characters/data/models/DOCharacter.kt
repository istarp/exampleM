package nz.co.example.rickandmortymodule.features.characters.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import nz.co.example.rickandmortymodule.features.characters.business.models.Character

//TODO ideally should be another entity class so it is not mixed with Api Data Object
@Serializable
@Entity(tableName = "characters")
internal data class DOCharacter(
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val image: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: DOOrigin,
    val location: DOLocation,
    val isFavourite: Boolean = false
) {
    @Serializable
    internal data class DOOrigin(
        val name: String,
        val url: String
    )

    @Serializable
    internal data class DOLocation(
        val name: String,
        val url: String
    )
}

internal fun mapFrom(data: DOCharacter): Character {
    return Character(
        id = data.id,
        name = data.name,
        imageUrl = data.image,
        status = data.status,
        species = data.species,
        type = data.type,
        gender = data.gender,
        origin = data.origin.name,
        location = data.location.name,
        isFavourite = data.isFavourite
    )
}

@ProvidedTypeConverter
internal class OriginConverter {
    @TypeConverter
    fun stringToOrigin(string: String): DOCharacter.DOOrigin {
        return Json.decodeFromString(string)
    }

    @TypeConverter
    fun originToString(origin: DOCharacter.DOOrigin?): String {
       return  Json.encodeToString(origin)
    }
}

@ProvidedTypeConverter
internal class LocationConverter {
    @TypeConverter
    fun stringToLocation(string: String): DOCharacter.DOLocation {
        return Json.decodeFromString(string)
    }

    @TypeConverter
    fun locationToString(origin: DOCharacter.DOLocation): String {
        return  Json.encodeToString(origin)
    }
}