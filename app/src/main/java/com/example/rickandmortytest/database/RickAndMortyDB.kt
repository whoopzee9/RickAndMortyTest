package com.example.rickandmortytest.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rickandmortytest.data.Character

@Database(entities = [Character::class], version = 1, exportSchema = false)
abstract class RickAndMortyDB: RoomDatabase() {
    abstract fun characters(): RickAndMortyDao
}