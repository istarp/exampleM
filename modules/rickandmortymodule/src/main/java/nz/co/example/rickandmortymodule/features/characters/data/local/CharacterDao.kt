package nz.co.example.rickandmortymodule.features.characters.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import nz.co.example.rickandmortymodule.features.characters.data.models.DOCharacter

@Dao
internal interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(users: List<DOCharacter>)

    @Query("SELECT * FROM characters")
    fun getCharacters(): PagingSource<Int, DOCharacter>

    @Query("SELECT * FROM characters WHERE id = :id")
    fun getCharacter(id: String): Flow<DOCharacter?>

    @Query("SELECT * FROM characters WHERE id = :id")
    suspend fun retrieveCharacter(id: String): DOCharacter?

    @Query("DELETE FROM characters")
    suspend fun clearAll()
}