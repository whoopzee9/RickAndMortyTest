package com.example.rickandmortytest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
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
import java.text.SimpleDateFormat

class RecyclerAdapter(var onClickListener: OnClickListener): PagingDataAdapter<Character, RecyclerViewHolder>(DataDifferntiator) {

    interface OnClickListener {
        fun onItemClick(item: Character)
        fun updateSharedPrefs(id: Int, value: Boolean)
        fun showToast(message: String)
        fun deleteFavourite(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_row_layout, parent, false)
        return RecyclerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            if (holder.absoluteAdapterPosition >= itemCount) {
                onClickListener.showToast("Data is updating")
            } else {
                getItem(holder.absoluteAdapterPosition)?.let { it1 -> onClickListener.onItemClick(it1) }
            }
        }
        holder.image.load(getItem(position)?.image) {
            transformations(CircleCropTransformation())
        }
        holder.name.text = getItem(position)?.name
        holder.origin.text = getItem(position)?.origin?.name

        holder.likeButton.isLiked = getItem(position)?.isFavourite ?: false

        holder.likeButton.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                getItem(holder.absoluteAdapterPosition)?.isFavourite = true
                onClickListener.updateSharedPrefs(getItem(holder.absoluteAdapterPosition)!!.id, true)
                GlobalScope.launch(Dispatchers.IO) {
                    NetworkService.instance.getCharactersTable().insertCharacter(getItem(holder.absoluteAdapterPosition)!!)
                }
            }

            override fun unLiked(likeButton: LikeButton?) {
                getItem(holder.absoluteAdapterPosition)?.isFavourite = false
                onClickListener.updateSharedPrefs(getItem(holder.absoluteAdapterPosition)!!.id, false)
                GlobalScope.launch(Dispatchers.IO) {
                    NetworkService.instance.getCharactersTable().deleteCharacter(getItem(holder.absoluteAdapterPosition)!!)
                    withContext(Dispatchers.Main) {
                        onClickListener.deleteFavourite(holder.absoluteAdapterPosition)
                    }
                }
            }

        })
    }

    object DataDifferntiator : DiffUtil.ItemCallback<Character>() {

        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }
}