package nz.co.example.nbamodule.features.nbaplayers.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import nz.co.example.nbamodule.features.nbaplayers.data.local.models.EntityNBAPlayerRemoteKeys

@Dao
internal interface NBAPlayerRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeysEntity: List<EntityNBAPlayerRemoteKeys>)

    @Query("SELECT * FROM nba_players_remote_keys WHERE playerId = :playerId")
    suspend fun remoteKeysRepoId(playerId: Int): EntityNBAPlayerRemoteKeys?

    @Query("DELETE FROM nba_players_remote_keys")
    suspend fun clearAll()

    @Query("SELECT * FROM nba_players_remote_keys ORDER BY playerId ASC LIMIT 1")
    suspend fun getFirstRemoteKey(): EntityNBAPlayerRemoteKeys?

}