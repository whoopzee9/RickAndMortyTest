package com.example.rickandmortytest.data

import android.net.Uri

data class Character(var name: String,
                     var status: String,
                     var species: String,
                     var origin: String,
                     var gender: String,
                     var image: Uri)
