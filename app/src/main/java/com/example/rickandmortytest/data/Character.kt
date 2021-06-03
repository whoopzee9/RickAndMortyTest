package com.example.rickandmortytest.data

import android.os.Parcelable
import androidx.room.*
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
    var isFavourite: Boolean = false): Parcelable {
        constructor(
            id: Int,
            name: String,
            status: String,
            species: String,
            gender: String,
            origin: Location,
            image: String,
            isFavourite: Boolean): this(
            id = id,
            name = name,
            status = status,
            species = species,
            type = "",
            gender = gender,
            origin = origin,
            location = Location("", ""),
            image = image,
            episode = emptyList<String>(),
            url = "",
            created = "",
            isFavourite = isFavourite)
}

@Parcelize
data class Location(var name: String, @Ignore var url: String): Parcelable {
    constructor(name: String): this(name = name, url = "")
}
