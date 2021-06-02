package com.example.rickandmortytest.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    var id: Int,
    var name: String,
    var status: String,
    var species: String,
    var type: String,
    var gender: String,
    var origin: Location,
    var location: Location,
    var image: String,
    var episode: List<String>,
    var url: String,
    var created: String,
    var isFavourite: Boolean = false): Parcelable

@Parcelize
data class Location(var name: String, var url: String): Parcelable
