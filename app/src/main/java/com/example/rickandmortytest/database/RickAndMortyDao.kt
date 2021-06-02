package com.example.rickandmortytest.database

import androidx.paging.DataSource
import androidx.room.*
import com.example.rickandmortytest.data.Character

@Dao
interface RickAndMortyDao {

    @Query("SELECT * FROM characters")
    fun getAllFavouriteCharacters(): DataSource.Factory<Int, Character>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(character: Character)

    @Delete
    fun deleteCharacter(character: Character)
}