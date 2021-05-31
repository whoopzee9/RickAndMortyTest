package com.example.rickandmortytest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.rickandmortytest.R
import com.example.rickandmortytest.data.Character
import com.like.LikeButton
import com.like.OnLikeListener
import java.text.SimpleDateFormat

class RecyclerAdapter(var onClickListener: OnClickListener): PagingDataAdapter<Character, RecyclerViewHolder>(DataDifferntiator) {

    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_row_layout, parent, false)
        return RecyclerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onClickListener.onItemClick(position)
        }
        holder.image.load(getItem(position)?.image) {
            transformations(CircleCropTransformation())
        }
        holder.name.text = getItem(position)?.name
        holder.origin.text = getItem(position)?.origin?.name

        holder.likeButton.isLiked = getItem(position)?.isFavourite ?: false

        holder.likeButton.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                getItem(position)?.isFavourite = true
            }

            override fun unLiked(likeButton: LikeButton?) {
                getItem(position)?.isFavourite = false
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