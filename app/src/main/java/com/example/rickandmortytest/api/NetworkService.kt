package com.example.rickandmortytest.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkService {
    companion object {
        val instance : NetworkService by lazy { holder.Instance }
    }

    private object holder {
        val Instance = NetworkService()
    }

    private val BASE_URL = "https://rickandmortyapi.com/api/"
    private var retrofitApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RickAndMortyApi::class.java)

    fun getRickAndMortyApi() = retrofitApi
}