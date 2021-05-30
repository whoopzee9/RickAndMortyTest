package com.example.rickandmortytest.repositories

import androidx.lifecycle.MutableLiveData

class ListRepository {
    companion object {
        val instance : ListRepository by lazy { holder.Instance }
    }

    private object holder {
        val Instance = ListRepository()
    }

    var isFavourites: MutableLiveData<Boolean> = MutableLiveData(false)

}