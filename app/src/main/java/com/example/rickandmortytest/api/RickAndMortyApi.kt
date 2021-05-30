package com.example.rickandmortytest.api

import com.example.rickandmortytest.data.Character
import com.example.rickandmortytest.data.Result
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharacters(): Response<Result>
}