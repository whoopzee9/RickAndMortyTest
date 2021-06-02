package com.example.rickandmortytest.data

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "characters")
@Parcelize
data class Character(
    @PrimaryKey
    var id: Int,
    var name: String,
    var status: String,
    var species: String,
    @Ignore
    var type: String,
    var gender: String,
    @Embedded(prefix = "origin")
    var origin: Location,
    @Ignore
    var location: Location,
    var image: String,
    @Ignore
    var episode: List<String>,
    @Ignore
    var url: String,
    @Ignore
    var created: String,
    var isFavourite: Boolean = false): Parcelable

@Parcelize
data class Location(var name: String, @Ignore var url: String): Parcelable
