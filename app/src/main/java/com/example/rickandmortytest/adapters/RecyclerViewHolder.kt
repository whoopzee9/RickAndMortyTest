package com.example.rickandmortytest.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortytest.R
import com.example.rickandmortytest.databinding.ListRowLayoutBinding
import com.like.LikeButton

class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var image: ImageView = itemView.findViewById(R.id.iv_character_picture)
    var name: TextView = itemView.findViewById(R.id.tv_name)
    var origin: TextView = itemView.findViewById(R.id.tv_origin)
    var likeButton: LikeButton = itemView.findViewById(R.id.lb_like_button)

}