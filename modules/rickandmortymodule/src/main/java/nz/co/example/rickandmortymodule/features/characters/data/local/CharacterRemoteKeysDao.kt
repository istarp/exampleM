package nz.co.example.rickandmortymodule.features.characters.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import nz.co.example.rickandmortymodule.features.characters.data.models.DOCharacterRemoteKeys

@Dao
internal interface CharacterRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeysEntity: List<DOCharacterRemoteKeys>)

    @Query("SELECT * FROM characters_remote_keys WHERE characterId = :characterId")
    suspend fun remoteKeysRepoId(characterId: Int): DOCharacterRemoteKeys?

    @Query("DELETE FROM characters_remote_keys")
    suspend fun clearAll()

    @Query("SELECT * FROM characters_remote_keys ORDER BY characterId ASC LIMIT 1")
    suspend fun getFirstRemoteKey(): DOCharacterRemoteKeys?

}