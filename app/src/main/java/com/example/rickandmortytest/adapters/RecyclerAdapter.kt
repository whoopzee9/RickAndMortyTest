package com.example.rickandmortytest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import coil.load
import coil.transform.CircleCropTransformation
import com.example.rickandmortytest.R
import com.example.rickandmortytest.api.NetworkService
import com.example.rickandmortytest.data.Character
import com.like.LikeButton
import com.like.OnLikeListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecyclerAdapter(var eventHandler: EventHandler) :
    PagingDataAdapter<Character, RecyclerViewHolder>(DataDifferentiator) {

    interface EventHandler {
        fun onItemClick(item: Character)
        fun updateSharedPrefs(id: Int, value: Boolean)
        fun deleteFavourite(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_row_layout, parent, false)
        return RecyclerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            getItem(holder.absoluteAdapterPosition)?.let { it1 -> eventHandler.onItemClick(it1) }
        }
        holder.image.load(getItem(position)?.image) {
            transformations(CircleCropTransformation())
        }
        holder.name.text = getItem(position)?.name
        holder.origin.text = getItem(position)?.origin?.name

        holder.likeButton.isLiked = getItem(position)?.isFavourite ?: false

        holder.likeButton.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                val pos = holder.absoluteAdapterPosition
                getItem(pos)?.isFavourite = true
                eventHandler.updateSharedPrefs(getItem(pos)!!.id, true)
                GlobalScope.launch(Dispatchers.IO) {
                    NetworkService.instance.getCharactersTable()
                        .insertCharacter(getItem(pos)!!)
                }
            }

            override fun unLiked(likeButton: LikeButton?) {
                val pos = holder.absoluteAdapterPosition
                getItem(pos)?.isFavourite = false
                eventHandler.updateSharedPrefs(getItem(pos)!!.id, false)
                GlobalScope.launch(Dispatchers.IO) {
                    NetworkService.instance.getCharactersTable()
                        .deleteCharacter(getItem(pos)!!)
                    withContext(Dispatchers.Main) {
                        eventHandler.deleteFavourite(pos)
                    }
                }

            }

        })
    }

    object DataDifferentiator : DiffUtil.ItemCallback<Character>() {

        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }
}