package nz.co.example.nbamodule.features.nbaplayers.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import nz.co.example.nbamodule.features.nbaplayers.data.local.models.EntityNBAPlayer

@Dao
internal interface NBAPlayerDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(users: List<EntityNBAPlayer>)

    @Query("SELECT * FROM nba_players")
    fun getPlayers(): PagingSource<Int, EntityNBAPlayer>

    @Query("SELECT * FROM nba_players WHERE id = :id")
    fun getPlayer(id: String): Flow<EntityNBAPlayer?>

    @Query("SELECT * FROM nba_players WHERE id = :id")
    suspend fun retrievePlayer(id: String): EntityNBAPlayer?

    @Query("DELETE FROM nba_players")
    suspend fun clearAll()
}