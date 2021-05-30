package com.example.rickandmortytest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.rickandmortytest.R
import com.example.rickandmortytest.data.Character
import java.text.SimpleDateFormat

class RecyclerAdapter(var values: ArrayList<Character>, var onClickListener: OnClickListener): RecyclerView.Adapter<RecyclerViewHolder>() {

    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    override fun getItemCount(): Int {
        return this.values.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_row_layout, parent, false)
        return RecyclerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onClickListener.onItemClick(position)
        }
//        holder.image.load(values[position].image) {
//            transformations(CircleCropTransformation())
//        }
        holder.name.text = values[position].name
        holder.origin.text = values[position].origin
    }
}