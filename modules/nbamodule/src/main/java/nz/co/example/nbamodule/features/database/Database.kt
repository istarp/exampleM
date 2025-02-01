package nz.co.example.nbamodule.features.database

import androidx.room.Database
import androidx.room.RoomDatabase
import nz.co.example.nbamodule.features.nbaplayers.data.local.NBAPlayerDao
import nz.co.example.nbamodule.features.nbaplayers.data.local.NBAPlayerRemoteKeysDao
import nz.co.example.nbamodule.features.nbaplayers.data.local.models.EntityNBAPlayer
import nz.co.example.nbamodule.features.nbaplayers.data.local.models.EntityNBAPlayerRemoteKeys

@Database(entities = [EntityNBAPlayer::class, EntityNBAPlayerRemoteKeys::class], version = 1, exportSchema = false)
internal abstract class Database : RoomDatabase() {
    abstract val nbaPlayersDao: NBAPlayerDao
    abstract val nbaPlayersRemoteKeysDao: NBAPlayerRemoteKeysDao
}