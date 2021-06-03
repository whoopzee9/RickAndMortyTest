package com.example.rickandmortytest.api

import android.content.Context
import androidx.room.Room
import com.example.rickandmortytest.database.RickAndMortyDB
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkService {
    companion object {
        val instance: NetworkService by lazy { holder.Instance }
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

    private lateinit var database: RickAndMortyDB

    fun getRickAndMortyApi() = retrofitApi

    fun initDatabase(context: Context) {
        database = Room.databaseBuilder(context, RickAndMortyDB::class.java, "RickAndMortyDB")
            .build()
    }

    fun getCharactersTable() = database.characterDao()
}