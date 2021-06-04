package com.example.rickandmortytest.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmortytest.data.Character

class InformationViewModel : ViewModel() {

    private val characterInfo: MutableLiveData<Character> = MutableLiveData()
    val characterLiveData: LiveData<Character> = characterInfo

    fun setCharacterInfo(character: Character?) {
        characterInfo.postValue(character)
    }
}