package com.example.rickandmortytest.database

import androidx.room.*
import com.example.rickandmortytest.data.Character

@Dao
interface RickAndMortyDao {

    @Query("SELECT * FROM characters")
    fun getAllFavouriteCharacters(): List<Character>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(character: Character)

    @Delete
    fun deleteCharacter(character: Character)
}