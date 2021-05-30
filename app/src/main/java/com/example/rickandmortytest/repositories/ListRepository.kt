package com.example.rickandmortytest.repositories

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.rickandmortytest.api.NetworkService
import com.example.rickandmortytest.data.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.await
import retrofit2.awaitResponse

class ListRepository {
    companion object {
        val instance : ListRepository by lazy { holder.Instance }
    }

    private object holder {
        val Instance = ListRepository()
    }

    var isFavourites: MutableLiveData<Boolean> = MutableLiveData(false)
    var characterList: MutableLiveData<List<Character>> = MutableLiveData()
    var isLoadingData: MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        val api = NetworkService.instance.getRickAndMortyApi()
        GlobalScope.launch(Dispatchers.IO) {
            isLoadingData.postValue(true)
            val response = api.getCharacters()
            if (response.isSuccessful) {
                characterList.postValue(response.body()?.results)
            } else {
                Log.d("ListRepository", response.code().toString())
            }
            isLoadingData.postValue(false)
        }
    }
}