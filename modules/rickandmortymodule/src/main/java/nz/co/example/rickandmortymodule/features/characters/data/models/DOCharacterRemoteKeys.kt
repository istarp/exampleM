package nz.co.example.rickandmortymodule.features.characters.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters_remote_keys")
internal data class DOCharacterRemoteKeys(
    @PrimaryKey val characterId: Int,
    val prevKey: Int?,
    val nextKey: Int?,
    val lastUpdated: Long
)
