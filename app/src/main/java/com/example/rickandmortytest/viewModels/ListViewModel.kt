package com.example.rickandmortytest.viewModels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.rickandmortytest.repositories.ListPageSource
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class ListViewModel(sharedPreferences: SharedPreferences) : ViewModel() {

    val characterList = Pager(PagingConfig(pageSize = 20)) {
        ListPageSource(sharedPreferences)
    }.flow.cachedIn(viewModelScope) //liveData.cachedIn(viewModelScope)

    private var isFavouritesMutableLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    val isFavourites: LiveData<Boolean> = isFavouritesMutableLiveData

    fun setFavourites() = isFavouritesMutableLiveData.postValue(!isFavourites.value!!)

}