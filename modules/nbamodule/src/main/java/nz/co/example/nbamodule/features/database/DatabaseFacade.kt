package nz.co.example.nbamodule.features.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import nz.co.example.nbamodule.features.nbaplayers.data.local.NBAPlayerDao
import nz.co.example.nbamodule.features.nbaplayers.data.local.NBAPlayerRemoteKeysDao
import nz.co.example.nbamodule.features.nbaplayers.data.local.models.EntityNBAPlayer
import nz.co.example.nbamodule.features.nbaplayers.data.local.models.EntityNBAPlayerRemoteKeys
import nz.co.example.nbamodule.features.nbaplayers.data.local.models.TeamConverter

@Database(entities = [EntityNBAPlayer::class, EntityNBAPlayerRemoteKeys::class], version = 1, exportSchema = false)
@TypeConverters(TeamConverter::class)
internal abstract class DatabaseFacade : RoomDatabase() {
    abstract val nbaPlayersDao: NBAPlayerDao
    abstract val nbaPlayersRemoteKeysDao: NBAPlayerRemoteKeysDao
}