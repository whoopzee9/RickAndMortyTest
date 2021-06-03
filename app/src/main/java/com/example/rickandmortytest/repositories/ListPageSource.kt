package com.example.rickandmortytest.repositories

import android.content.SharedPreferences
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortytest.api.NetworkService
import com.example.rickandmortytest.data.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ListPageSource(private val sharedPreferences: SharedPreferences) :
    PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        try {
            val pageNumber = params.key ?: 1
            val api = NetworkService.instance.getRickAndMortyApi()
            val response = api.getCharacters(pageNumber)
            if (response.isSuccessful) {
                val characters = response.body()?.results ?: emptyList()
                characters.forEach {
                    val isFavourite = sharedPreferences.getBoolean(it.id.toString(), false)
                    it.isFavourite = isFavourite
                }
                val prevKey = if (pageNumber == 1) null else pageNumber - 1
                val nextKey =
                    if (pageNumber < response.body()!!.info.pages) pageNumber + 1 else null
                return LoadResult.Page(characters, prevKey, nextKey)
            } else {
                return LoadResult.Error(HttpException(response))
            }
        } catch (e: Exception) {
            if (params.key == null) { //Nothing has been loaded
                val db = NetworkService.instance.getCharactersTable()
                var characters: List<Character> = emptyList()
                val res = GlobalScope.launch(Dispatchers.IO) {
                    characters = db.getAllFavouriteCharacters()
                }
                res.join()
                return LoadResult.Page(characters, null, null)
            } else {
                return LoadResult.Error(e)
            }
        }
    }
}