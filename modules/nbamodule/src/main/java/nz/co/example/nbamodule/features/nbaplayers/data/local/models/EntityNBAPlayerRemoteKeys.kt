package nz.co.example.nbamodule.features.nbaplayers.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nba_players_remote_keys")
internal data class EntityNBAPlayerRemoteKeys(
    @PrimaryKey val playerId: Int,
    val prevKey: Int?,
    val nextKey: Int?,
    val lastUpdated: Long
)

internal fun mapFrom(playerId: Int, prevKey: Int?, nextKey: Int?): EntityNBAPlayerRemoteKeys {
    return EntityNBAPlayerRemoteKeys(
        playerId = playerId,
        prevKey = prevKey,
        nextKey = nextKey,
        lastUpdated = System.currentTimeMillis()
    )
}