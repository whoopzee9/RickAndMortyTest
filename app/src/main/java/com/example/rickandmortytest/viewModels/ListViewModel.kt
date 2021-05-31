package com.example.rickandmortytest.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.rickandmortytest.api.NetworkService
import com.example.rickandmortytest.data.Character
import com.example.rickandmortytest.repositories.ListPageSource
import com.example.rickandmortytest.repositories.ListRepository

class ListViewModel : ViewModel() {

    val characterList = Pager(PagingConfig(pageSize = 20)) {
        ListPageSource()
    }.flow.cachedIn(viewModelScope)

    //private var repository = ListRepository.instance

//    fun isFavouritesLiveData(): LiveData<Boolean> = repository.isFavourites
//    fun setFavourites() = repository.isFavourites.postValue(!isFavouritesLiveData().value!!)
//
//    fun getCharactersListLiveData(): LiveData<List<Character>> = repository.characterList
//    fun isLoadingLiveData(): LiveData<Boolean> = repository.isLoadingData

}