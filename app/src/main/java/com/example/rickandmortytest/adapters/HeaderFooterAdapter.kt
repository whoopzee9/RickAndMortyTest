package com.example.rickandmortytest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortytest.R

class HeaderFooterAdapter: LoadStateAdapter<HeaderFooterAdapter.HeaderFooterViewHolder>() {
    override fun onBindViewHolder(holder: HeaderFooterViewHolder, loadState: LoadState) {
        holder.itemView.isVisible = loadState == LoadState.Loading
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): HeaderFooterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.progress_row_layout, parent, false)
        return HeaderFooterViewHolder(itemView)
    }

    class HeaderFooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}