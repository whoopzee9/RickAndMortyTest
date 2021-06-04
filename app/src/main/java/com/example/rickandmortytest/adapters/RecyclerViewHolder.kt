package com.example.rickandmortytest.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortytest.R
import com.like.LikeButton

class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var image: ImageView = itemView.findViewById(R.id.list_iv_character_image)
    var name: TextView = itemView.findViewById(R.id.list_tv_name)
    var origin: TextView = itemView.findViewById(R.id.list_tv_origin)
    var likeButton: LikeButton = itemView.findViewById(R.id.list_lb_like_button)

}