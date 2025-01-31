package nz.co.example.rickandmortymodule.features.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import nz.co.example.rickandmortymodule.features.characters.data.local.CharacterDao
import nz.co.example.rickandmortymodule.features.characters.data.local.CharacterRemoteKeysDao
import nz.co.example.rickandmortymodule.features.characters.data.models.DOCharacter
import nz.co.example.rickandmortymodule.features.characters.data.models.DOCharacterRemoteKeys
import nz.co.example.rickandmortymodule.features.characters.data.models.TeamConverter

@Database(entities = [DOCharacter::class, DOCharacterRemoteKeys::class], version = 1, exportSchema = false)
@TypeConverters(TeamConverter::class)
internal abstract class Database : RoomDatabase() {
    abstract val charactersDao: CharacterDao
    abstract val charactersRemoteKeysDao: CharacterRemoteKeysDao
}