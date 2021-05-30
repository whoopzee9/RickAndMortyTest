package com.example.rickandmortytest.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmortytest.repositories.ListRepository

class ListViewModel : ViewModel() {
    private var repository = ListRepository.instance

    fun isFavourites(): LiveData<Boolean> = repository.isFavourites
    fun setFavourites() = repository.isFavourites.postValue(!isFavourites().value!!)
}