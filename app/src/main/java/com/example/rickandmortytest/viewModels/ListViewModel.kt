package com.example.rickandmortytest.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmortytest.data.Character
import com.example.rickandmortytest.repositories.ListRepository

class ListViewModel : ViewModel() {
    private var repository = ListRepository.instance

    fun isFavouritesLiveData(): LiveData<Boolean> = repository.isFavourites
    fun setFavourites() = repository.isFavourites.postValue(!isFavouritesLiveData().value!!)

    fun getCharactersListLiveData(): LiveData<List<Character>> = repository.characterList
    fun isLoadingLiveData(): LiveData<Boolean> = repository.isLoadingData
}